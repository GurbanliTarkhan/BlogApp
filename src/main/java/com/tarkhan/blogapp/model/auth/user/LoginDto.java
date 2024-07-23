package com.tarkhan.blogapp.model.auth.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {

    @NotBlank(message = "Username must not be blank")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
