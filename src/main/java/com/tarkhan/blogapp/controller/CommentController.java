package com.tarkhan.blogapp.controller;

import com.tarkhan.blogapp.constants.Constants;
import com.tarkhan.blogapp.model.ResponseModel;
import com.tarkhan.blogapp.model.comment.CommentDto;
import com.tarkhan.blogapp.model.comment.CreateCommentDto;
import com.tarkhan.blogapp.model.comment.UpdateCommentDto;
import com.tarkhan.blogapp.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    @Operation(summary = "Get All Comments")
    public ResponseEntity<Page<CommentDto>> getAllComments(
            @Valid @RequestParam int page,
            @Valid @RequestParam int size) {
        Page<CommentDto> comments = commentService.getComments(page, size);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Comment by ID")
    public ResponseEntity<CommentDto> getCommentById(@Valid @PathVariable Long id) {
        CommentDto comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    @PostMapping
    @Operation(summary = "Create Comment")
    public ResponseEntity<ResponseModel> createComment(
            @Valid @RequestBody CreateCommentDto createCommentDto
    ) {
        commentService.addComment(createCommentDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseModel(
                        Constants.STATUS_201,
                        Constants.MESSAGE_201
                ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Comment by ID")
    public ResponseEntity<ResponseModel> updateComment(
            @Valid @PathVariable Long id,
            @Valid @RequestBody UpdateCommentDto updateCommentDto) {
        commentService.updateComment(id, updateCommentDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseModel(
                        Constants.STATUS_200,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                ));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Comment by ID")
    public ResponseEntity<ResponseModel> deleteComment(@Valid @PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ResponseModel(
                        Constants.STATUS_200,
                        Constants.MESSAGE_200
                ));
    }
}
