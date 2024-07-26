package com.tarkhan.blogapp.service;

import com.tarkhan.blogapp.model.follow.FollowDto;
import com.tarkhan.blogapp.model.follow.FollowRequestDto;

import java.util.List;

public interface FollowService {
    FollowDto followUser(FollowRequestDto followRequestDto);
    void unfollowUser(Long followerId, Long followingId);
    List<FollowDto> getFollowers(Long userId);
    List<FollowDto> getFollowing(Long userId);
}
