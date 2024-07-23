package com.tarkhan.blogapp.controller;

import com.tarkhan.blogapp.constants.Constants;
import com.tarkhan.blogapp.entity.Profile;
import com.tarkhan.blogapp.model.ResponseModel;
import com.tarkhan.blogapp.model.profile.ProfileDto;
import com.tarkhan.blogapp.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileDto> getProfile(@RequestHeader("Authorization") String token) {
        ProfileDto profile = profileService.getProfileByUserId(token);
        return ResponseEntity.ok(profile);

    }

    @PutMapping
    public ResponseEntity<ResponseModel> updateProfile(@RequestHeader("Authorization") String token,
                                                 @RequestBody ProfileDto profileDto) {
        profileService.updateProfile(token, profileDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_200,
                        Constants.MESSAGE_200
                )
        );
    }
}
