package com.tarkhan.blogapp.model.auth;

import lombok.Data;

import java.util.List;

@Data
public class JwtDto {
    private List<String> roles;
    private Long userId;
}
