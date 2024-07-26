package com.tarkhan.blogapp.model.post;

import com.tarkhan.blogapp.entity.Comment;
import com.tarkhan.blogapp.entity.Tag;
import com.tarkhan.blogapp.entity.User;
import com.tarkhan.blogapp.model.tag.GetTagDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPostDto {
    private Long id;

    private String title;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long author_id;
}
