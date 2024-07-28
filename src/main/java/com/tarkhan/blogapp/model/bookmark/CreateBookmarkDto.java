package com.tarkhan.blogapp.model.bookmark;

import lombok.Data;

@Data
public class CreateBookmarkDto {
    private Long userId;
    private Long postId;
}
