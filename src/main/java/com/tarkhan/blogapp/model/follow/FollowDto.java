package com.tarkhan.blogapp.model.follow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowDto {
    private Long id;
    private Long followerId;
    private Long followingId;
}
