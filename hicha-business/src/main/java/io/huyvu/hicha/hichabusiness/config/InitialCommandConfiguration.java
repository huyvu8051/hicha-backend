package io.huyvu.hicha.hichabusiness.config;

import io.huyvu.hicha.hichabusiness.model.MessageInsert;
import io.huyvu.hicha.hichabusiness.repository.MessageRepository;
import io.huyvu.hicha.hichabusiness.service.JsonPlaceholderService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class InitialCommandConfiguration {

    @Bean
    @Observed(name = "posts.load-all-posts", contextualName = "post.find-all")
    CommandLineRunner commandLineRunner(JsonPlaceholderService jsonPlaceholderService) {
        return args -> {
            jsonPlaceholderService.findAll();
        };
    }
}
