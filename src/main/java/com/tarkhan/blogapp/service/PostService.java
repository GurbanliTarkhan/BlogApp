package com.tarkhan.blogapp.service;

import com.tarkhan.blogapp.model.post.AddPostDto;
import com.tarkhan.blogapp.model.post.GetPostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    GetPostDto createPost(AddPostDto postDto, MultipartFile photoFile);
    GetPostDto updatePost(Long postId, AddPostDto postDto, MultipartFile photoFile);
    void deletePost(Long postId);
    GetPostDto getPostById(Long postId);
    List<GetPostDto> getAllPosts();
}
