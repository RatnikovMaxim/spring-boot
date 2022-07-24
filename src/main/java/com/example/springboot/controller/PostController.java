package com.example.springboot.controller;

import com.example.springboot.dto.PostRequestDTO;
import com.example.springboot.dto.PostResponseDTO;
import com.example.springboot.manager.PostManager;
import com.example.springboot.security.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostManager manager;

    @GetMapping("/posts")
    public List<PostResponseDTO> getAll(@RequestAttribute final Authentication authentication) {
        final List<PostResponseDTO> responseDTO = manager.getAll(authentication);
        return responseDTO;
    }
    @GetMapping("/posts/{id}")
    public PostResponseDTO getById(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id) {
        final PostResponseDTO responseDTO = manager.getById(id, authentication);
        return responseDTO;
    }

    @PostMapping("/posts")
    public PostResponseDTO create(@RequestAttribute final Authentication authentication,
                                  @Valid @RequestBody final PostRequestDTO requestDTO) {
        final PostResponseDTO responseDTO = manager.create(requestDTO, authentication);
        return responseDTO;
    }

    @PutMapping("/posts")
    public PostResponseDTO update(@RequestAttribute final Authentication authentication,
                                  @Valid @RequestBody final PostRequestDTO requestDTO) {
        final PostResponseDTO responseDTO = manager.update(requestDTO, authentication);
        return responseDTO;
    }

    @DeleteMapping("/posts/{id}")
    public void deleteById(@RequestAttribute final Authentication authentication,
                           @Min(1) @PathVariable final long id) {
        manager.deleteById(id, authentication);
    }
}


