package com.tarkhan.blogapp.service.auth;

import com.tarkhan.blogapp.model.auth.AuthResponse;
import com.tarkhan.blogapp.model.auth.user.*;

public interface AuthService {
    AuthResponse register(RegisterDto request);
    AuthResponse login(LoginDto request);
    GetAccountDto getAccount(String email);
    AuthResponse changePassword(String token, ChangePasswordDto request);
}
