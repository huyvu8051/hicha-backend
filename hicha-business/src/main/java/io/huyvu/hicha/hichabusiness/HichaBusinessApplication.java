package io.huyvu.hicha.hichabusiness;

import io.huyvu.hicha.hichabusiness.service.JsonPlaceholderService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class HichaBusinessApplication {
	public static void main(String[] args) {
		SpringApplication.run(HichaBusinessApplication.class, args);
	}
}
