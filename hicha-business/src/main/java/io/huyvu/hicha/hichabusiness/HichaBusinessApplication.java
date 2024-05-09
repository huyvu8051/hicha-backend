package io.huyvu.hicha.hichabusiness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories()
public class HichaBusinessApplication {
	public static void main(String[] args) {
		SpringApplication.run(HichaBusinessApplication.class, args);
	}
}
