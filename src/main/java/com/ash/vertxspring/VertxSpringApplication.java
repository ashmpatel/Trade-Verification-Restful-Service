package com.ash.vertxspring;

import com.ash.vertxspring.verticles.ServerVerticle;
import com.ash.vertxspring.verticles.TradeVerificationVerticle;
import io.vertx.core.Vertx;
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

    @Autowired
    private ServerVerticle serverVerticle;

    @Autowired
    private TradeVerificationVerticle serviceVerticle;

    public static void main(String[] args) {
        SpringApplication.run(VertxSpringApplication.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
        final Vertx vertx = Vertx.vertx();

        // start the Vertx server verticle
        vertx.deployVerticle(serverVerticle);

        // deploy one verticle for the RESTful serivce
        vertx.deployVerticle(serviceVerticle);
    }
}
