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

    ScenarioBuilder myFirstScenario = scenario("My Second Scenario")
            .feed(feeder)
            .exec(http("Request insert")
                    .post("/api/v1/message")
                    .body(StringBody("""
                        {
                          "conversationId": 1,
                          "senderId": 1,
                          "messageText": "#{messageText}"
                        }""")))
            .exec(http("Request get")
                    .get("/api/v1/message/1"));

    {
        setUp(
                myFirstScenario.injectOpen( nothingFor(4), // 1
                        atOnceUsers(1000), // 2
                        rampUsers(1000).during(5), // 3
                        constantUsersPerSec(2000).during(15), // 4
                        constantUsersPerSec(2000).during(15).randomized(), // 5
                        rampUsersPerSec(1000).to(20).during(10), // 6
                        rampUsersPerSec(1000).to(20).during(10).randomized(), // 7
                        stressPeakUsers(10000).during(20) // 8)
        ).protocols(httpProtocol));
    }

}