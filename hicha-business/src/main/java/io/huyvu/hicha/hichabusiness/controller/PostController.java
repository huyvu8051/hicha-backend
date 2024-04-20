package io.huyvu.hicha.hichabusiness.controller;

import io.huyvu.hicha.hichabusiness.service.JsonPlaceholderService;
import io.huyvu.hicha.hichabusiness.service.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final JsonPlaceholderService jsonPlaceholderService;

    @GetMapping
    List<Post> findAll() {
        return jsonPlaceholderService.findAll();
    }


    @GetMapping("/{id}")
    Post findById(@PathVariable Integer id) {
        return jsonPlaceholderService.findById(id);
    }
}
