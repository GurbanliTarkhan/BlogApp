package com.tarkhan.blogapp.model.auth.user;

import com.tarkhan.blogapp.entity.Comment;
import com.tarkhan.blogapp.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Post> posts;
    private List<Comment> comments;
}
