package io.huyvu.hicha.controller;

import io.huyvu.hicha.IntegrationTest;
import io.huyvu.hicha.controller.MessageController.ConversationDetails;
import io.huyvu.hicha.controller.MessageController.MessageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class MessageControllerIntegrationTest extends IntegrationTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void returnNoMessage() {
        var result = restTemplate.getForObject("/api/v1/message/0", ConversationDetails.class);
        assertThat(result.getMessages()).isEmpty();
    }

    @Test
    void returnAtLeastOneMessage() {
        var response = restTemplate.getForObject("/api/v1/message/1", ConversationDetails.class);
        assertThat(response.getMessages()).isNotEmpty();
    }

    @Test
    void sendMessage() {
        String messageText = faker.lorem().paragraph(faker.number().numberBetween(1, 10));
        restTemplate.postForObject("/api/v1/message", new MessageDTO(1, 1, messageText), String.class);
        var response = restTemplate.getForObject("/api/v1/message/1", ConversationDetails.class);
        assertThat(response.getMessages().getFirst().getMessageText()).isEqualTo(messageText);
    }

    @Test
    void returnGreaterThan1() {
        var response = restTemplate.getForObject("/api/v1/message/1", ConversationDetails.class);
        assertThat(response.getMessages()).size().isGreaterThan(1);
    }
}