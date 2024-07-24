package com.tarkhan.blogapp.controller;

import com.tarkhan.blogapp.model.post.AddPostDto;
import com.tarkhan.blogapp.model.post.GetPostDto;
import com.tarkhan.blogapp.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "Create a new post",
            requestBody = @RequestBody(content = @Content(mediaType = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE,
                    schema = @Schema(implementation = AddPostDto.class))))
    public ResponseEntity<GetPostDto> createPost(
            @ModelAttribute AddPostDto postDto,
            @RequestParam("photoFile") MultipartFile photoFile) {
        GetPostDto createdPost = postService.createPost(postDto, photoFile);
        return ResponseEntity.ok(createdPost);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing post",
            requestBody = @RequestBody(content = @Content(mediaType = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE,
                    schema = @Schema(implementation = AddPostDto.class))))
    public ResponseEntity<GetPostDto> updatePost(
            @PathVariable Long id,
            @ModelAttribute AddPostDto postDto,
            @RequestParam("photoFile") MultipartFile photoFile) {
        GetPostDto updatedPost = postService.updatePost(id, postDto, photoFile);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a post by its ID")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a post by its ID")
    public ResponseEntity<GetPostDto> getPostById(@PathVariable Long id) {
        GetPostDto post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    @Operation(summary = "Get all posts")
    public ResponseEntity<List<GetPostDto>> getAllPosts() {
        List<GetPostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
}
