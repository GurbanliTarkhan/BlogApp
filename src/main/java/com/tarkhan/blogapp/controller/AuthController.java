package com.tarkhan.blogapp.controller;

import com.tarkhan.blogapp.constants.Constants;
import com.tarkhan.blogapp.model.auth.user.*;
import com.tarkhan.blogapp.model.auth.AuthResponse;
import com.tarkhan.blogapp.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterDto request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account")
    public ResponseEntity<GetAccountDto> getAccount(@RequestParam String email) {
        return ResponseEntity.ok(authService.getAccount(email));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<AuthResponse> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordDto request
    ) {
        authService.changePassword(token, request);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(
                Constants.STATUS_200,
                Constants.MESSAGE_UPDATE_SUCCESSFUL,
                token
        ));
    }
}
