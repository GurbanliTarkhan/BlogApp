package com.tarkhan.blogapp.service.impl;

import com.tarkhan.blogapp.entity.Follow;
import com.tarkhan.blogapp.entity.User;
import com.tarkhan.blogapp.exception.BlogApiException;
import com.tarkhan.blogapp.exception.ResourceNotFoundException;
import com.tarkhan.blogapp.model.follow.FollowDto;
import com.tarkhan.blogapp.model.follow.FollowRequestDto;
import com.tarkhan.blogapp.repository.FollowRepository;
import com.tarkhan.blogapp.repository.UserRepository;
import com.tarkhan.blogapp.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public FollowDto followUser(FollowRequestDto followRequestDto) {
        User follower = userRepository.findById(followRequestDto.getFollowerId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", followRequestDto.getFollowerId()));

        User following = userRepository.findById(followRequestDto.getFollowingId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", followRequestDto.getFollowingId()));

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new BlogApiException("Already following this user");
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();

        Follow savedFollow = followRepository.save(follow);

        return modelMapper.map(savedFollow, FollowDto.class);
    }

    @Override
    public void unfollowUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", followerId));

        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", followingId));

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Follow", "followerId and followingId", followerId + " and " + followingId)
                );

        followRepository.delete(follow);
    }

    @Override
    public List<FollowDto> getFollowers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return followRepository.findByFollowing(user).stream()
                .map(follow -> modelMapper.map(follow, FollowDto.class))
                .collect(Collectors.toList());    }

    @Override
    public List<FollowDto> getFollowing(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return followRepository.findByFollower(user).stream()
                .map(follow -> modelMapper.map(follow, FollowDto.class))
                .collect(Collectors.toList());
    }
}
