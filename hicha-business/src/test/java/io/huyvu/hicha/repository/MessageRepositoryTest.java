package io.huyvu.hicha.repository;

import com.github.javafaker.Faker;
import io.huyvu.hicha.repository.model.Message;
import io.huyvu.hicha.repository.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.AutoConfigureDataCassandra;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataCassandraTest
@AutoConfigureDataCassandra
@Slf4j
class MessageRepositoryTest {
    @Container
    @ServiceConnection
    static MariaDBContainer<?> cassandraDatabase = new MariaDBContainer<>("mariadb:11.3.2").withDatabaseName("hicha");

    @Autowired
    MessageRepository repository;

    static Faker faker = new Faker(Locale.of("vi"));


//    @Test
    void shouldReturnMessages() {
        Iterable<Message> messages = repository.findByConversationId(1);
        messages.forEach(e -> log.info("item: {}", e.toString()));
        assertThat(2).isGreaterThan(1);
    }

}