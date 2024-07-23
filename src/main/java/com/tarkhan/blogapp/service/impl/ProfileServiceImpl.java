package com.tarkhan.blogapp.service.impl;

import com.tarkhan.blogapp.entity.Profile;
import com.tarkhan.blogapp.exception.BlogApiException;
import com.tarkhan.blogapp.exception.ResourceNotFoundException;
import com.tarkhan.blogapp.model.auth.JwtDto;
import com.tarkhan.blogapp.model.profile.ProfileDto;
import com.tarkhan.blogapp.repository.ProfileRepository;
import com.tarkhan.blogapp.repository.UserRepository;
import com.tarkhan.blogapp.service.ProfileService;
import com.tarkhan.blogapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    @Override
    public ProfileDto getProfileByUserId(String token) {
        try {
            JwtDto jwtDto = jwtUtil.decodeToken(token);
            Long userId = jwtDto.getUserId();

            Profile profile = profileRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile", "User ID", userId));
            return modelMapper.map(profile, ProfileDto.class);
        } catch (Exception e) {
            throw new BlogApiException("Error getting profile: " + e.getMessage());
        }
    }

    @Override
    public Profile updateProfile(String token, ProfileDto profileDetails) {
        try {
            JwtDto jwtDto = jwtUtil.decodeToken(token);
            Long userId = jwtDto.getUserId();
            Profile profile = profileRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile", "User ID", userId));

            if (profileDetails.getBiography() != null) {
                profile.setBiography(profileDetails.getBiography());
            }
            if (profileDetails.getWebsite() != null) {
                profile.setWebsite(profileDetails.getWebsite());
            }

            return profileRepository.save(profile);
        } catch (Exception e) {
            throw new BlogApiException("Error updating profile: " + e.getMessage());
        }
    }

    @Override
    public Profile createProfile(Long userId) {
        Profile profile = new Profile();
        profile.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId)));

        return profileRepository.save(profile);
    }
}
