package io.huyvu.hicha.hichabusiness.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Service
public class JsonPlaceholderServiceImpl{
    @Bean
    JsonPlaceholderService jsonPlaceholderService() {
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
        HttpServiceProxyFactory fact = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return fact.createClient(JsonPlaceholderService.class);
    }
}
