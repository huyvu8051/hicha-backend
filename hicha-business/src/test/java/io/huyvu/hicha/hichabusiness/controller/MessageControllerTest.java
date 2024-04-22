package io.huyvu.hicha.hichabusiness.controller;

import io.huyvu.hicha.hichabusiness.config.SecurityConfig;
import io.huyvu.hicha.hichabusiness.model.MessageInsert;
import io.huyvu.hicha.hichabusiness.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({MessageController.class, SecurityConfig.class})
@AutoConfigureMockMvc
@Slf4j
class MessageControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MessageRepository messageRepository;

    @Test
    void shouldReturnAll() throws Exception {
        when(messageRepository.insertMessage(any(MessageInsert.class))).thenReturn(1L);


        String requestBody = """
                             {
                                "conversationId": 1,
                                "messageText": "Test message"
                             }
                             """;

        mockMvc.perform(post("/api/v1/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

    }
}