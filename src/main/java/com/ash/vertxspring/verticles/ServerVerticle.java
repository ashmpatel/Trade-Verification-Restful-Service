package com.ash.vertxspring.verticles;

import com.ash.vertxspring.entity.TradeRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

@Component
public class ServerVerticle extends AbstractVerticle {

    private static final String CONTENT_TYPE = "content-type";
    private static final String JSON_RESPONSE = "application/json";
    private static final String TEXT_RESPONSE = "text/html";
    private static final String HEALTH_CHECK = "<h1>Service is UP</h1>";

    private static final int OK_RESPONSE    = 200;
    private static final int ERROR_RESPONSE = 500;

    private static final Logger LOGGER = LogManager.getLogger(ServerVerticle.class.getName());

    @Autowired
    private Integer defaultPort;

    private void verifyTrade(RoutingContext routingContext) {

        //if trade message is empty, do not progress further
        if (routingContext.getBodyAsString().length() == 0) {
            routingContext.response()
                    .putHeader(CONTENT_TYPE, JSON_RESPONSE)
                    .setStatusCode(ERROR_RESPONSE)
                    .end("");
        } else {
            // decode the JSON sent to a TradeRequest object
            final TradeRequest tradeRequest = Json.decodeValue(routingContext.getBodyAsString(),
                    TradeRequest.class);

           LOGGER.info("RECEIVED : " + tradeRequest.toString());

            vertx.eventBus()
                    .<String>send(TradeVerificationVerticle.VERIFY_TRADE, tradeRequest, result -> {
                        if (result.succeeded()) {
                            if (result.result().body().length() != 0) {
                                routingContext.response()
                                        .setStatusCode(ERROR_RESPONSE)
                                        .end(result.result().body().toString());
                            } else {
                                routingContext.response()
                                        .putHeader(CONTENT_TYPE, JSON_RESPONSE)
                                        .setStatusCode(OK_RESPONSE)
                                        .end("OK");
                            }
                        }
                    });
        }
    }

    @Override
    public void start() throws Exception {
        super.start();

        Router router = Router.router(vertx);

        // Bind "/" to a health message
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader(CONTENT_TYPE, TEXT_RESPONSE)
                    .end(HEALTH_CHECK);
        });

        // accept body in the post messages
        router.route().handler(BodyHandler.create());

        // route all calls to verify trade to the verifytrade verticle
        router.post("/api/v1/verifytrade").handler(this::verifyTrade);

        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(config().getInteger("http.port", defaultPort));
    }

}
