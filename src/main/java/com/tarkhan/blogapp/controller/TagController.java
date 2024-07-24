package com.tarkhan.blogapp.controller;

import com.tarkhan.blogapp.constants.Constants;
import com.tarkhan.blogapp.entity.Tag;
import com.tarkhan.blogapp.model.ResponseModel;
import com.tarkhan.blogapp.model.tag.GetTagByPostDto;
import com.tarkhan.blogapp.model.tag.GetTagDto;
import com.tarkhan.blogapp.service.TagService;
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
    public ResponseEntity<Page<GetTagDto>> getAllTags(
            @RequestParam int page,
            @RequestParam int size) {
        Page<GetTagDto> tags = tagService.getTags(page, size);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTagDto> getTag(@PathVariable Long id) {
        GetTagDto tag = tagService.getTagById(id);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/by-name")
    public ResponseEntity<GetTagDto> getTagByName(@RequestParam String name) {
        GetTagDto result = tagService.getTagByName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<GetTagDto>> searchTags(@RequestParam String name){
        List<GetTagDto> tag = tagService.getSearchTags(name);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/byb-post")
    public ResponseEntity<GetTagByPostDto> getTagsByPost(@RequestParam Long id){
        GetTagByPostDto tag = tagService.getTagByPosts(id);
        return ResponseEntity.ok(tag);
    }

    @PostMapping
    public ResponseEntity<ResponseModel> createTag(@RequestParam String tagName) {
        tagService.addTag(tagName);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseModel(
                        Constants.STATUS_200,
                        Constants.MESSAGE_201
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel> updateTag(
            @RequestParam Long id,
            @RequestParam String tagName
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
    public ResponseEntity<ResponseModel> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
