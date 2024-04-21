package io.huyvu.hicha.hichabusiness.config;

import io.huyvu.hicha.hichabusiness.service.JsonPlaceholderService;
import io.micrometer.observation.annotation.Observed;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialCommandConfiguration {
    @Bean
    @Observed(name = "posts.load-all-posts", contextualName = "post.find-all")
    CommandLineRunner commandLineRunner(JsonPlaceholderService jsonPlaceholderService) {
        return args -> {
            jsonPlaceholderService.findAll();
        };
    }
}
