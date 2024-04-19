package io.huyvu.hicha.hichabusiness.controller;

import io.huyvu.hicha.hichabusiness.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class HomeController {
    private final JdbcClient jdbcClient;

    @GetMapping
    String home() {
        return "Hello World!";
    }

    @PostMapping("new")
    void newUser(@RequestBody UserDTO user) {
        jdbcClient.sql("insert into user(id, name) values ($1, $2)")
                .param(user.id(), user.name())
                .update();
    }
}