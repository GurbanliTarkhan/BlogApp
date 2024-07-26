package com.tarkhan.blogapp.controller;

import com.tarkhan.blogapp.constants.Constants;
import com.tarkhan.blogapp.model.ResponseModel;
import com.tarkhan.blogapp.model.follow.FollowDto;
import com.tarkhan.blogapp.model.follow.FollowRequestDto;
import com.tarkhan.blogapp.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping
    @Operation(summary = "Follow a User")
    public ResponseEntity<ResponseModel> followUser(
            @Valid @RequestBody FollowRequestDto followRequestDto
    ) {
        followService.followUser(followRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseModel(
                        Constants.STATUS_201,
                        Constants.MESSAGE_201
                ));
    }

    @DeleteMapping
    @Operation(summary = "Unfollow a User")
    public ResponseEntity<ResponseModel> unfollowUser(
            @Valid @RequestParam Long followerId,
            @Valid @RequestParam Long followingId) {
        followService.unfollowUser(followerId, followingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/followers/{userId}")
    @Operation(summary = "Get Followers of a User")
    public ResponseEntity<List<FollowDto>> getFollowers(@Valid @PathVariable Long userId) {
        List<FollowDto> followers = followService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following/{userId}")
    @Operation(summary = "Get Following of a User")
    public ResponseEntity<List<FollowDto>> getFollowing(@Valid @PathVariable Long userId) {
        List<FollowDto> following = followService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }
}
