package io.huyvu.hicha;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Locale;

@Slf4j
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Container
    @ServiceConnection
    static MariaDBContainer<?> mariaDBContainer =  new MariaDBContainer<>("mariadb:latest")
            .withDatabaseName("hicha");

    static Faker faker = new Faker(Locale.of("vi"));

    @BeforeEach
    public void setup() {
    }
}
