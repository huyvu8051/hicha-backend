package io.huyvu.hicha.hichabusiness.config;

import org.springframework.boot.autoconfigure.cassandra.CassandraConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CassandraConfig {
    @Bean
    @Primary
    CassandraConnectionDetails mainConnection(CassandraConnectionDetails cassandraConnectionDetailsForCassandra0){
        return cassandraConnectionDetailsForCassandra0;
    }
}
