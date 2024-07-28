package com.tarkhan.blogapp.model.bookmark;

import lombok.Data;

@Data
public class BookmarkDto {
    private Long id;
    private Long userId;
    private Long postId;
}
