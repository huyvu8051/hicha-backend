package io.huyvu.hicha.hichabusiness.repository;

import io.huyvu.hicha.hichabusiness.controller.HomeController;
import io.huyvu.hicha.hichabusiness.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
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
        List<UserDTO> userDTOS = List.of(new UserDTO(null, "Son Tung M-TP"), new UserDTO(null, "Hieu Thu Hai"), new UserDTO(null, "Truc Nhan"));
        for (var userDTO : userDTOS) {
            userRepository.save(HomeController.UserMapper.INSTANCE.toEntity(userDTO));
        }
    }

    @Test
    void shouldReturnAllUser() {
        List<UserDTO> all = userRepository.findAll();
        assertThat(all).isNotNull().isNotEmpty();
    }


}