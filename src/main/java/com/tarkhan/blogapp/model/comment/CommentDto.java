package com.tarkhan.blogapp.model.comment;


import com.tarkhan.blogapp.model.auth.user.GetUserDto;
import com.tarkhan.blogapp.model.post.GetPostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private GetUserDto author;
    private GetPostDto post;
}
