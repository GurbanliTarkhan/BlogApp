package com.tarkhan.blogapp.model.follow;

import lombok.Data;

@Data
public class FollowRequestDto {
    private Long followerId;
    private Long followingId;
}
