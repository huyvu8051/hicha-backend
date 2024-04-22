package io.huyvu.hicha.hichabusiness.repository;

import com.github.javafaker.Faker;
import io.huyvu.hicha.hichabusiness.model.MessageInsert;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class MessageRepositoryTest {
    @Container
    @ServiceConnection
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest")
            .withDatabaseName("hicha");

    @Autowired
    MessageRepository repository;

    static Faker faker = new Faker(Locale.of("vi"));

    @Test
    void shouldInsertSuccess() {
        var beforeInsert = repository.selectMessages(1, 0, Integer.MAX_VALUE);
        var messageInsert = new MessageInsert(1l, 1l, faker.lorem().sentence());
        repository.insertMessage(messageInsert);
        var lastInsertId = repository.selectLastInsertId();
        var afterInsert = repository.selectMessages(1, 0, Integer.MAX_VALUE);

        assertThat(lastInsertId).isEqualTo(afterInsert.getLast().messageId());
        assertThat(afterInsert.size() - beforeInsert.size()).isEqualTo(1);
        assertThat(messageInsert).isEqualToComparingFieldByField(afterInsert.getLast());
    }

    @Test
    void shouldReturnAtLeaseOne() {
        var messages = repository.selectMessages(1, 0, Integer.MAX_VALUE);
        assertThat(messages).isNotEmpty();
    }


}