package io.huyvu.hicha.hichabusiness.controller;

import io.huyvu.hicha.hichabusiness.service.JsonPlaceholderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({PostController.class, JsonPlaceholderServiceImpl.class})
@AutoConfigureMockMvc
@Slf4j
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;



    @WithMockUser("huyvu")
    @Test
    void shouldReturnAll() throws Exception {
        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(1))));
    }

    @Test
    @WithMockUser("huyvu")
    void shouldReturnOne() throws Exception {
        mockMvc.perform(get("/api/v1/posts/1"))
                .andExpect(status().isOk())
                .andDo((result -> {
                    log.info(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
                }));
    }
}