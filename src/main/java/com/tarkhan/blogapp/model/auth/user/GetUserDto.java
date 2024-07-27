package com.tarkhan.blogapp.model.auth.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
    private Long id;
    private String username;
    private String email;
}
