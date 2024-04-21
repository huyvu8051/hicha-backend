package io.huyvu.hicha.hichabusiness.repository;

import io.huyvu.hicha.hichabusiness.model.UserDTO;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class UserRepositoryTest {
    @Container
    @ServiceConnection
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest")
            .withDatabaseName("hicha");


    @Autowired
    private UserRepository userRepository;

    @Test
    void connectionEstablished() {
        assertThat(mariadb.isCreated()).isTrue();
        assertThat(mariadb.isRunning()).isTrue();
    }

    @BeforeEach
    void setup() {
        List<UserDTO> userDTOS = List.of(new UserDTO(4L, "Son Tung M-TP"), new UserDTO(2L, "Hieu Thu Hai"), new UserDTO(3L, "Truc Nhan"));
        for (UserDTO userDTO : userDTOS) {
            userRepository.save(userDTO);
        }
    }

    @Test
    void shouldReturnAllUser() {
        List<UserDTO> all = userRepository.findAll();
        assertThat(all).isNotNull().isNotEmpty();
    }


}