package io.huyvu.hicha.hichabusiness.controller;

import io.huyvu.hicha.hichabusiness.model.UserDTO;
import io.huyvu.hicha.hichabusiness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final UserRepository userRepository;

    @GetMapping
    List<UserDTO> getAllUser() {
        return userRepository.findAll();
    }

    @PostMapping
    String newUser(@RequestBody UserDTO user) {
        log.info("new user: {}", user);
        userRepository.save(user);
        return "Success";
    }


    @GetMapping("{id}")
    UserDTO getUser(@PathVariable long id) {
        return userRepository.findById(id);
    }
}