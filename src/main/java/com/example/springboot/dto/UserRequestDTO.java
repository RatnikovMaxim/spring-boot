package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserRequestDTO {
    private long id;
    private String login;
    private String password;
    private List<String> role;
}