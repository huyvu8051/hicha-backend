package io.huyvu.hicha.repository;

import com.github.javafaker.Faker;
import io.huyvu.hicha.repository.model.Message;
import io.huyvu.hicha.repository.repo.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class MessageRepositoryImplTest {

    static Faker faker = new Faker(Locale.of("vi"));
    @Container
    @ServiceConnection
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest")
            .withDatabaseName("hicha");


    @Autowired
    private MessageRepository messageRepository;

    @Test
    void connectionEstablished() {
        assertThat(mariadb.isCreated()).isTrue();
        assertThat(mariadb.isRunning()).isTrue();
    }

    @BeforeEach
    void setup() {
        for (var i : IntStream.range(0, 10).toArray()) {
            messageRepository.save(new Message(null, 1L, 1L, i + " " + faker.lorem().paragraph(faker.number().numberBetween(1, 10)), Instant.now()));
        }
    }

    @Test
    void shouldReturnMoreThan10Items() {
        List<Message> all = messageRepository.findByConversationId(1);
        assertThat(all).hasSizeGreaterThan(10);
    }

    @Test
    void shouldReturnEmptyList() {
        List<Message> all = messageRepository.findByConversationId(2);
        assertThat(all).isEmpty();
    }


}