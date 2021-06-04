package com.ash.vertxspring.verticles;

import com.ash.vertxspring.entity.TradeRequest;
import com.ash.vertxspring.entity.TradeVerificationResponse;
import com.ash.vertxspring.exceptions.InvalidTradeMessage;
import com.ash.vertxspring.response.GenericCodec;
import com.ash.vertxspring.service.TradeVerificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TradeVerificationVerticle extends AbstractVerticle {

    // these are the paths I will respond to on the Vertx event bus
    public static final String VERIFY_TRADE = "verify.trade";

    private static final Logger LOGGER = LogManager.getLogger(TradeVerificationVerticle.class.getName());

    @Autowired
    public TradeVerificationService tradeVerificationService;

    @Override
    public void start() throws Exception {

        super.start();

        // register a codec for the TradeObject to put the message on the wire
        vertx.eventBus().registerDefaultCodec(TradeRequest.class,
                new GenericCodec<>(TradeRequest.class));

        // consumer for trade verification trade requests
        MessageConsumer<TradeRequest> consumer = vertx.eventBus().consumer(VERIFY_TRADE);
        consumer.handler(
                message -> vertx.<String>executeBlocking(future -> {
                    String result = verifyTradeRequest(tradeVerificationService, message.body());

                    // result will be a list of exceptions
                    if (result == null || result.length() == 0) {
                        //send a result if you want
                        future.complete("");
                    } else {
                         // send back the cause of the failure
                        future.fail(result);
                    }
                }, result -> {
                    if (result.succeeded()) {
                        // request was processed successfully so send back result of processing
                        LOGGER.info("Responded to request - Success");
                        message.reply(result.result());
                    } else {
                        // there was an error, log error , send back the trade and message as the
                        // requirement was to attach the trade to the list of exceptions
                        LOGGER.error("Error in the trade request - returning exceptions");
                        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

                        // convert the response to Json as this is what were shipping back
                        TradeVerificationResponse tradeResponse = new TradeVerificationResponse(message.body(), result.cause()
                                .toString());
                        try {
                            String json = ow.writeValueAsString(tradeResponse);
                            message.reply(json);
                        } catch (JsonProcessingException e) {
                            LOGGER.error("Error processing Json");
                        }

                    }
                }));


        LOGGER.info("Started Service");
    }

    // verify the trade data given the service implementation
    private String verifyTradeRequest(TradeVerificationService service, TradeRequest tradeRequest) {
        StringBuilder errorMessage = new StringBuilder();

        // verify the trade, get back errors
        List<InvalidTradeMessage> errorMessages = service.verifyTrade(tradeRequest);

        // convert all exceptions to string to send back
        for (InvalidTradeMessage message : errorMessages) {
            errorMessage = errorMessage.append(message.toString());
            errorMessage.append("\n");
        }

        return errorMessage.toString();
    }
}
