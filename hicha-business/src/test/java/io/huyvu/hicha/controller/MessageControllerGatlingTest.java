package io.huyvu.hicha.controller;

import static org.junit.jupiter.api.Assertions.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static scala.Predef.Map;

import com.github.javafaker.Faker;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MessageControllerGatlingTest extends Simulation {

    Faker faker = new Faker(Locale.of("vi"));
    Iterator<Map<String, Object>> feeder =
            Stream.generate(() -> {
                        Object text = faker.lorem().paragraph(faker.number().numberBetween(1, 10));
                        return Map.of("messageText", text);
                    }
            ).iterator();

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8080")
                    .acceptHeader("application/json")
                    .contentTypeHeader("application/json");

    ScenarioBuilder myFirstScenario = scenario("My First Scenario")
            .exec(http("Request get")
                    .get("/api/v1/message/1"));

    ScenarioBuilder mySecondScenario = scenario("My Second Scenario")
            .feed(feeder)
            .exec(http("Request insert")
                    .post("/api/v1/message")
                    .body(StringBody("""
                        {
                          "conversationId": 1,
                          "senderId": 1,
                          "messageText": "#{messageText}"
                        }""")));

    {
        setUp(
                myFirstScenario.injectOpen(constantUsersPerSec(1000).during(5)),
                mySecondScenario.injectOpen(constantUsersPerSec(1000).during(5))
        ).protocols(httpProtocol);
    }

}