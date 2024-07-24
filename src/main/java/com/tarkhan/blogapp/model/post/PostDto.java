package com.tarkhan.blogapp.model.post;

import com.tarkhan.blogapp.entity.*;
import jakarta.persistence.*;

import java.util.List;

public class PostDto {

    private Long id;

    private String title;
    private String content;
    private Long photoId;
    private Long userId;
    private List<Long> tagIds;
    private Long categoryId;
}
