package com.tarkhan.blogapp.service;

import com.tarkhan.blogapp.model.comment.CommentDto;
import com.tarkhan.blogapp.model.comment.CreateCommentDto;
import com.tarkhan.blogapp.model.comment.UpdateCommentDto;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<CommentDto> getComments(int page, int size);
    CommentDto getCommentById(Long id);
    void addComment(CreateCommentDto createCommentDto);
    void updateComment(Long id, UpdateCommentDto updateCommentDto);
    void deleteComment(Long id);
}
