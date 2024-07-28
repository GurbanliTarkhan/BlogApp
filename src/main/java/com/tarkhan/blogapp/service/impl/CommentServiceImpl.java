package com.tarkhan.blogapp.service.impl;

import com.tarkhan.blogapp.entity.Comment;
import com.tarkhan.blogapp.entity.Post;
import com.tarkhan.blogapp.entity.User;
import com.tarkhan.blogapp.exception.ResourceNotFoundException;
import com.tarkhan.blogapp.model.comment.CommentDto;
import com.tarkhan.blogapp.model.comment.CreateCommentDto;
import com.tarkhan.blogapp.model.comment.UpdateCommentDto;
import com.tarkhan.blogapp.repository.CommentRepository;
import com.tarkhan.blogapp.repository.PostRepository;
import com.tarkhan.blogapp.repository.UserRepository;
import com.tarkhan.blogapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<CommentDto> getComments(int page, int size) {
        return commentRepository.findAll(PageRequest.of(page, size))
                .map(comment -> modelMapper.map(comment, CommentDto.class));
    }

    @Override
    public CommentDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", id));
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public void addComment(CreateCommentDto createCommentDto) {
        Comment comment = modelMapper.map(createCommentDto, Comment.class);

        User author = userRepository.findById(createCommentDto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", createCommentDto.getAuthorId()));
        Post post = postRepository.findById(createCommentDto.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", createCommentDto.getPostId()));

        comment.setAuthor(author);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    @Override
    public void updateComment(Long id, UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", id));

        comment.setContent(updateCommentDto.getContent());
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", id));

        commentRepository.delete(comment);
    }
}