package com.tarkhan.blogapp.service;

import com.tarkhan.blogapp.entity.Profile;
import com.tarkhan.blogapp.model.profile.ProfileDto;

public interface ProfileService {

    ProfileDto getProfileByUserId(String token);
    Profile updateProfile(String token, ProfileDto profileDetails);
    Profile createProfile(Long userId);
}
