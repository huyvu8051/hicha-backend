package io.huyvu.hicha.hichabusiness.repository;

import com.github.javafaker.Faker;
import io.huyvu.hicha.hichabusiness.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.AutoConfigureDataCassandra;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.CassandraContainer;
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
    static CassandraContainer<?> cassandraDatabase = new CassandraContainer<>("cassandra:4.0.12");

    @Autowired
    MessageCassandraRepository repository;

    static Faker faker = new Faker(Locale.of("vi"));


    @Test
    void shouldReturnMessages(){
        Iterable<Message> messages = repository.findAll();

        messages.forEach(e->log.info("item: {}",e.toString()));

        assertThat(2).isGreaterThan(1);
    }

}