package com.tarkhan.blogapp.service.auth.impl;

import com.tarkhan.blogapp.constants.Constants;
import com.tarkhan.blogapp.entity.Profile;
import com.tarkhan.blogapp.entity.Role;
import com.tarkhan.blogapp.entity.User;
import com.tarkhan.blogapp.exception.BlogApiException;
import com.tarkhan.blogapp.exception.ResourceNotFoundException;
import com.tarkhan.blogapp.model.auth.AuthResponse;
import com.tarkhan.blogapp.model.auth.JwtDto;
import com.tarkhan.blogapp.model.auth.user.ChangePasswordDto;
import com.tarkhan.blogapp.model.auth.user.GetAccountDto;
import com.tarkhan.blogapp.model.auth.user.LoginDto;
import com.tarkhan.blogapp.model.auth.user.RegisterDto;
import com.tarkhan.blogapp.model.profile.ProfileDto;
import com.tarkhan.blogapp.repository.UserRepository;
import com.tarkhan.blogapp.service.ProfileService;
import com.tarkhan.blogapp.service.auth.AuthService;
import com.tarkhan.blogapp.service.auth.JwtService;
import com.tarkhan.blogapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final ProfileService profileService;

    @Override
    public AuthResponse register(RegisterDto request) {
        User user = modelMapper.map(request, User.class);
        user.setRole(Role.USER);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        user = userRepository.save(user);

        profileService.createProfile(user.getId());

        String token = jwtService.generateToken(user);
        return new AuthResponse(Constants.STATUS_200, Constants.MESSAGE_REGISTER_SUCCESSFUL, token);
    }

    @Override
    public AuthResponse login(LoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email", "Email", request.getEmail()));

        String token = jwtService.generateToken(user);
        return new AuthResponse(Constants.STATUS_200, Constants.MESSAGE_LOGIN_SUCCESSFUL, token);
    }

    @Override
    public GetAccountDto getAccount(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email", "Email", email));
        return modelMapper.map(user, GetAccountDto.class);
    }

    @Override
    public AuthResponse changePassword(String token, ChangePasswordDto request) {
        try {
            JwtDto jwtDto = jwtUtil.decodeToken(token);
            Long userId = jwtDto.getUserId();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));

            // Old password authentication
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            request.getOldPassword()
                    )
            );

            // Password update
            if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(request.getNewPassword());
                user.setPassword(hashedPassword);
            }

            user.setUpdatedAt(LocalDateTime.now());
            user = userRepository.save(user);

            String newToken = jwtService.generateToken(user);
            return new AuthResponse(Constants.STATUS_200, Constants.MESSAGE_UPDATE_SUCCESSFUL, newToken);

        } catch (Exception e) {
            throw new BlogApiException("Error changing password: " + e.getMessage());
        }
    }

    @Override
    public Profile updateProfile(String token, ProfileDto profileDetails) {
        return profileService.updateProfile(token, profileDetails);
    }
}
