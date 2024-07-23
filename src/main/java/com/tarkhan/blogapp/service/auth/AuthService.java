package com.tarkhan.blogapp.service.auth;

import com.tarkhan.blogapp.entity.Profile;
import com.tarkhan.blogapp.model.auth.AuthResponse;
import com.tarkhan.blogapp.model.auth.user.ChangePasswordDto;
import com.tarkhan.blogapp.model.auth.user.GetAccountDto;
import com.tarkhan.blogapp.model.auth.user.LoginDto;
import com.tarkhan.blogapp.model.auth.user.RegisterDto;
import com.tarkhan.blogapp.model.profile.ProfileDto;

public interface AuthService {

    AuthResponse register(RegisterDto request);

    AuthResponse login(LoginDto request);

    GetAccountDto getAccount(String email);

    AuthResponse changePassword(String token, ChangePasswordDto request);

    Profile updateProfile(String token, ProfileDto profileDetails);
}
