package io.huyvu.hicha;

import io.huyvu.hicha.model.ConversationDetails;
import io.huyvu.hicha.model.MessageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class HichaBusinessApplicationTests extends IntegrationTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void returnNoMessage(){
        var result = restTemplate.getForObject("/api/v1/message/0", ConversationDetails.class);
        assertThat(result.getMessages()).isEmpty();
    }

    @Test
    void sendMessage(){
        String messageText = faker.lorem().paragraph(faker.number().numberBetween(1, 10));
        restTemplate.postForObject("/api/v1/message", new MessageDTO(1, 1, messageText), String.class);
        var response = restTemplate.getForObject("/api/v1/message/1", ConversationDetails.class);
        assertThat(response.getMessages().getFirst().getMessageText()).isEqualTo(messageText);
    }

    @Test
    void returnAtLeaseOne(){
        var response = restTemplate.getForObject("/api/v1/message/1", ConversationDetails.class);
        assertThat(response.getMessages()).size().isGreaterThan(1);
    }
}