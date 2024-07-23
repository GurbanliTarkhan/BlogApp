package com.tarkhan.blogapp.model.auth.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

    @Email(message = "Please enter a valid email address.")
    @NotBlank(message = "Email must not be blank")
    private String email;

    private String username;
    private String password;
}

