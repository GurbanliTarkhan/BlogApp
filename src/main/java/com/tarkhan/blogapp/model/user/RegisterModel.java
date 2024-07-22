package com.tarkhan.blogapp.model.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterModel {

    @Column(unique = true, nullable = false)
    private String username;

    @Size(min = 5,max = 32, message = "password size should be between 5 and 32 digit or character")
    private String password;
    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;
}
