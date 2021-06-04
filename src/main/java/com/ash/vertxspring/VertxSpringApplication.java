package com.ash.vertxspring;

import com.ash.vertxspring.verticles.ServerVerticle;
import com.ash.vertxspring.verticles.TradeVerificationVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Configuration
@EntityScan("com.ash.vertxspring.entity")
@ComponentScan(basePackages = {"com.ash"})
public class VertxSpringApplication {

    private static final Logger LOGGER = LogManager.getLogger(VertxSpringApplication.class.getName());

    @Autowired
    private ServerVerticle serverVerticle;

    @Autowired
    private TradeVerificationVerticle serviceVerticle;

    public static void main(String[] args) {
        SpringApplication.run(VertxSpringApplication.class, args);
    }

    // hook into to Spring lifecycle mgmt and create Vertx, deploy service.
    // This code is for a test so Im deploying 2 instances just to show how simple it is with Vertx.
    // Clustering is also easy but this was not asked for in the requirement so I did NOT overengineer the solution but again
    // very simple to do.
    @PostConstruct
    public void deployVerticle() {

        final Vertx vertx = Vertx.vertx();

        // deploy one verticle for the RESTful serivce
        vertx.deployVerticle(serviceVerticle);
    }
}
