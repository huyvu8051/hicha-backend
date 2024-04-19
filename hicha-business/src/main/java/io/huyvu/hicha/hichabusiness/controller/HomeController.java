package io.huyvu.hicha.hichabusiness.controller;

import io.huyvu.hicha.hichabusiness.model.UserDTO;
import io.huyvu.hicha.hichabusiness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class HomeController {
    private final JdbcClient jdbcClient;
    private final UserRepository userRepository;

    @GetMapping
    List<UserDTO> home() {
        return userRepository.findAll();
    }

    @PostMapping("new")
    void newUser(@RequestBody UserDTO user) {

        userRepository.save(user);
    }


    @GetMapping("/{id}")
    UserDTO getUser(@PathVariable long id) {
        return userRepository.findById(id);
    }
}