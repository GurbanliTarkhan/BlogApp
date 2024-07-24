package com.tarkhan.blogapp.service.impl;

import com.tarkhan.blogapp.entity.*;
import com.tarkhan.blogapp.exception.ResourceNotFoundException;
import com.tarkhan.blogapp.model.post.AddPostDto;
import com.tarkhan.blogapp.model.post.GetPostDto;
import com.tarkhan.blogapp.repository.CategoryRepository;
import com.tarkhan.blogapp.repository.PostRepository;
import com.tarkhan.blogapp.repository.TagRepository;
import com.tarkhan.blogapp.repository.UserRepository;
import com.tarkhan.blogapp.service.PhotoFileService;
import com.tarkhan.blogapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final PhotoFileService photoFileService;
    private final ModelMapper modelMapper;

    @Override
    public GetPostDto createPost(AddPostDto postDto, MultipartFile photoFile) {
        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", postDto.getUserId()));

        PhotoFile photo = null;
        if (photoFile != null && !photoFile.isEmpty()) {
            Long photoId = photoFileService.uploadFile(photoFile);
            photo = new PhotoFile();
            photo.setId(photoId);
        }

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        List<Tag> tags = tagRepository.findAllById(postDto.getTagIds());

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .author(user)
                .photoFile(photo)
                .category(category)
                .tags(tags.stream().collect(Collectors.toSet()))
                .build();

        post = postRepository.save(post);

        return modelMapper.map(post, GetPostDto.class);
    }

    @Override
    public GetPostDto updatePost(Long postId, AddPostDto postDto, MultipartFile photoFile) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        if (postDto.getTitle() != null) {
            post.setTitle(postDto.getTitle());
        }
        if (postDto.getContent() != null) {
            post.setContent(postDto.getContent());
        }

        if (photoFile != null && !photoFile.isEmpty()) {
            if (post.getPhotoFile() != null) {
                photoFileService.deleteFile(post.getPhotoFile().getPath());
            }
            Long photoId = photoFileService.uploadFile(photoFile);
            PhotoFile photo = new PhotoFile();
            photo.setId(photoId);
            post.setPhotoFile(photo);
        }

        if (postDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(postDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
            post.setCategory(category);
        }

        if (postDto.getTagIds() != null && !postDto.getTagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(postDto.getTagIds());
            post.setTags(tags.stream().collect(Collectors.toSet()));
        }

        post.setUpdatedAt(LocalDateTime.now());

        post = postRepository.save(post);

        return modelMapper.map(post, GetPostDto.class);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        if (post.getPhotoFile() != null) {
            photoFileService.deleteFile(post.getPhotoFile().getPath());
        }

        postRepository.delete(post);
    }

    @Override
    public GetPostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        return modelMapper.map(post, GetPostDto.class);
    }

    @Override
    public List<GetPostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> modelMapper.map(post, GetPostDto.class)).collect(Collectors.toList());
    }
}
