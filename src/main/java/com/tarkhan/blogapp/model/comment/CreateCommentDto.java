package com.tarkhan.blogapp.model.comment;

import lombok.Data;

@Data
public class CreateCommentDto {
    private String content;
    private Long authorId;
    private Long postId;}
