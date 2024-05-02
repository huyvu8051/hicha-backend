package io.huyvu.hicha.hichabusiness.controller;

import io.huyvu.hicha.hichabusiness.mapper.UserMapper;
import io.huyvu.hicha.hichabusiness.model.UserDTO;
import io.huyvu.hicha.hichabusiness.model.UserEntity;
import io.huyvu.hicha.hichabusiness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getCredentials();
        log.info("new user: {} {}", user, principal);
        log.info("Thread: {}", Thread.currentThread());
        UserEntity entity = UserMapper.INSTANCE.toEntity(user);
        userRepository.save(entity);
        return "Success";
    }


    @GetMapping("{id}")
    UserDTO getUser(@PathVariable long id) {
        log.info("get user: {}", id);
        return userRepository.findById(id);
    }
}