package com.tarkhan.blogapp.controller;

import com.tarkhan.blogapp.constants.Constants;
import com.tarkhan.blogapp.entity.Tag;
import com.tarkhan.blogapp.model.ResponseModel;
import com.tarkhan.blogapp.model.tag.GetTagByPostDto;
import com.tarkhan.blogapp.model.tag.GetTagDto;
import com.tarkhan.blogapp.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    @Operation(summary = "Get All Tags")
    public ResponseEntity<Page<GetTagDto>> getAllTags(
           @Valid @RequestParam int page,
           @Valid @RequestParam int size) {
        Page<GetTagDto> tags = tagService.getTags(page, size);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Delete a post by its ID")
    public ResponseEntity<GetTagDto> getTag(@Valid @PathVariable Long id) {
        GetTagDto tag = tagService.getTagById(id);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/by-name")
    @Operation(summary = "Get a tag by Tag Name")
    public ResponseEntity<GetTagDto> getTagByName(@Valid @RequestParam String name) {
        GetTagDto result = tagService.getTagByName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    @Operation(summary = "Get a Tags by its Tag Name")
    public ResponseEntity<List<GetTagDto>> searchTags(@Valid @RequestParam String name){
        List<GetTagDto> tag = tagService.getSearchTags(name);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/by-post")
    @Operation(summary = "Get a Tag by its post")
    public ResponseEntity<GetTagByPostDto> getTagsByPost(@Valid @RequestParam Long id){
        GetTagByPostDto tag = tagService.getTagByPosts(id);
        return ResponseEntity.ok(tag);
    }

    @PostMapping
    @Operation(summary = "Create Tag")
    public ResponseEntity<ResponseModel> createTag(@Valid @RequestParam String tagName) {
        tagService.addTag(tagName);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseModel(
                        Constants.STATUS_200,
                        Constants.MESSAGE_201
                ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Tag by its ID")
    public ResponseEntity<ResponseModel> updateTag(
            @Valid @RequestParam Long id,
            @Valid @RequestParam String tagName
    ) {
        tagService.updateTag(id, tagName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseModel(
                        Constants.STATUS_201,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                ));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Tag by its ID")
    public ResponseEntity<ResponseModel> deleteTag(@Valid @PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
