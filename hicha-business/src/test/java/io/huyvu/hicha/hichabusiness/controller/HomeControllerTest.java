package io.huyvu.hicha.hichabusiness.controller;

import com.github.javafaker.Faker;
import io.huyvu.hicha.hichabusiness.model.UserDTO;
import io.huyvu.hicha.hichabusiness.repository.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Mock test
 */
@WebMvcTest(HomeController.class)
@AutoConfigureMockMvc
@Slf4j
class HomeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    List<UserDTO> userDTOs = new ArrayList<>();
    static Faker faker = new Faker(new Locale("vi"));

    @BeforeEach
    void setUp() {
        IntStream.range(0, 10).forEach((e)->userDTOs.add(new UserDTO(null, faker.name().fullName())));

    }

    @SneakyThrows
    @Test
    void shouldFindAllUsers(){
        when(userRepository.findAll()).thenReturn(userDTOs);

        mockMvc.perform(get("/api/v1"))
                .andExpect(status().isOk())
                .andDo((result -> {
                    log.info(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
                }));
    }
}