package com.ash.vertxspring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VertxSpringApplicationIntegrationTest {

    @Autowired
    private Integer port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testSendEmptyTradeMmessage() throws InterruptedException {

        String sample = "";
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        System.out.println("Response was :" + responseEntity.toString());

        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    @Test
    public void sendTradeRequestIsReceived() throws InterruptedException {

        String sample = "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2020-09-20\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-09-22\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void sendTradeRequestIsReceivedInvalidCustomer() throws InterruptedException {

        String sample = "{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-08-15\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        System.out.println("Response was :" + responseEntity.toString());
        assertEquals(500, responseEntity.getStatusCodeValue());

    }

    @Test
    public void sendTradeRequestIsReceivedInvalidCCY() throws InterruptedException {

        String sample = "{\"customer\":\"YODA1\",\"ccyPair\":\"EUR\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-08-15\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        System.out.println("Response was :" + responseEntity.toString());

        assertEquals(500, responseEntity.getStatusCodeValue());

    }

    @Test
    public void sendTradeRequestIsReceivedValid() throws InterruptedException {

        String sample = "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2020-10-12\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-10-14\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        System.out.println("Response was :" + responseEntity.toString());

        assertEquals(200, responseEntity.getStatusCodeValue());

    }


    @Test
    public void sendTradeRequestIsReceivedValidEurOption() throws InterruptedException {

        String sample = "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN\"," +
                "\"direction\":\"BUY\",\"strategy\":\"CALL\"," +
                "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\"," +
                "\"expiryDate\":\"2020-08-19\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                "\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        System.out.println("Response was :" + responseEntity.toString());

        assertEquals(200, responseEntity.getStatusCodeValue());

    }

    @Test
    public void sendTradeRequestIsReceivedInValidEurOption() throws InterruptedException {

        // there is no option type of just Vanilla , it needs to be VanillaOption
        String sample = "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"Vanilla\",\"style\":\"EUROPEAN\"," +
                "\"direction\":\"BUY\",\"strategy\":\"CALL\"," +
                "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\"," +
                "\"expiryDate\":\"2020-08-19\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                "\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        System.out.println("Response was :" + responseEntity.toString());

        assertEquals(500, responseEntity.getStatusCodeValue());

    }

    @Test
    public void testInValidEurOptionDatesWrong() throws InterruptedException {

        // there is no option type of just the text Vanilla , it needs to be VanillaOption
        String sample = "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN\"," +
                "\"direction\":\"BUY\",\"strategy\":\"CALL\"," +
                "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\"," +
                "\"expiryDate\":\"2020-08-23\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                "\"premiumDate\":\"2020-08-24\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        System.out.println("Response was :" + responseEntity.toString());

        assertEquals(500, responseEntity.getStatusCodeValue());

    }

    @Test
    public void sendTradeRequestIsReceivedInValidEurOptionType() throws InterruptedException {
        // no option style called EURO, has to be EUROPEAN or AMERICAN
        String sample = "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EURO\"," +
                "\"direction\":\"BUY\",\"strategy\":\"CALL\"," +
                "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\"," +
                "\"expiryDate\":\"2020-08-19\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                "\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        System.out.println("Response was :" + responseEntity.toString());
        assertEquals(500, responseEntity.getStatusCodeValue());

    }

    @Test
    public void testValidOptionDate() throws InterruptedException {

        String sample = "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN\"," +
                "\"direction\":\"BUY\",\"strategy\":\"CALL\"," +
                "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\"," +
                "\"expiryDate\":\"2020-08-19\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                "\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        assertEquals(200, responseEntity.getStatusCodeValue());

    }

    @Test
    public void testInvalidAmericalOptionTrade() throws InterruptedException {
        // this is a valid trade I took from the sample data provided
        String sample = "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"AMERICAN\",\"direction\":\"BUY\",\"strategy\":\"CALL\"," +
                "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-19\"," +
                "\"excerciseStartDate\":\"2020-08-12\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                "\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        assertEquals(500, responseEntity.getStatusCodeValue());

    }

    @Test
    public void testInvalidAmericalInvalidOptionTrade() throws InterruptedException {
        // exerciseDate is BEFORE the tradeDate so this will be an error
        String sample = "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"AMERICAN\",\"direction\":\"BUY\",\"strategy\":\"CALL\"," +
                "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-19\"," +
                "\"excerciseStartDate\":\"2020-08-05\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                "\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        assertEquals(500, responseEntity.getStatusCodeValue());

    }

    @Test
    public void testInvalidAmericalInvalidOptionTrade2() throws InterruptedException {
        // premiumDate is AFTER deliveryDate so this is an error
        String sample = "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"AMERICAN\",\"direction\":\"BUY\",\"strategy\":\"CALL\"," +
                "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-19\"," +
                "\"excerciseStartDate\":\"2020-08-12\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                "\"premiumDate\":\"2020-08-23\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        assertEquals(500, responseEntity.getStatusCodeValue());

    }

    @Test
    public void testInvalidAmericalInvalidOptionTrade3() throws InterruptedException {
        // premiumDate is AFTER deliveryDate so this is an error
        String sample = "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"AMERICAN\",\"direction\":\"BUY\",\"strategy\":\"CALL\"," +
                "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-19\"," +
                "\"excerciseStartDate\":\"2020-08-20\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                "\"premiumDate\":\"2020-08-23\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/verifytrade", sample, String.class);

        assertEquals(500, responseEntity.getStatusCodeValue());

    }

}


