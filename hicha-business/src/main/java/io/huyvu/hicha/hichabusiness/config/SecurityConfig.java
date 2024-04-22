package io.huyvu.hicha.hichabusiness.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(SessionManagementConfigurer::disable)
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers(  "/actuator/**", "/api/v1/user/**","/api/v1/message/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }


    @Bean
    UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("huyvu")
                        .password("{noop}password")
                        .roles("USER")
                        .build()
        );
    }
}
