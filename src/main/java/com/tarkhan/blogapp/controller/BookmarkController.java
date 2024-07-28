package com.tarkhan.blogapp.controller;

import com.tarkhan.blogapp.constants.Constants;
import com.tarkhan.blogapp.model.ResponseModel;
import com.tarkhan.blogapp.model.bookmark.BookmarkDto;
import com.tarkhan.blogapp.model.bookmark.CreateBookmarkDto;
import com.tarkhan.blogapp.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping
    @Operation(summary = "Get All Bookmarks")
    public ResponseEntity<List<BookmarkDto>> getAllBookmarks() {
        List<BookmarkDto> bookmarks = bookmarkService.getAllBookmarks();
        return ResponseEntity.ok(bookmarks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Bookmark by ID")
    public ResponseEntity<BookmarkDto> getBookmarkById(@PathVariable Long id) {
        BookmarkDto bookmark = bookmarkService.getBookmarkById(id);
        return ResponseEntity.ok(bookmark);
    }

    @PostMapping
    @Operation(summary = "Create Bookmark")
    public ResponseEntity<ResponseModel> createBookmark(@Valid @RequestBody CreateBookmarkDto createBookmarkDto) {
        BookmarkDto bookmark = bookmarkService.createBookmark(createBookmarkDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseModel(
                        Constants.STATUS_201,
                        Constants.MESSAGE_201
                ));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Bookmark by ID")
    public ResponseEntity<ResponseModel> deleteBookmark(@PathVariable Long id) {
        bookmarkService.deleteBookmark(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ResponseModel(
                        Constants.STATUS_204,
                        Constants.MESSAGE_204
                ));
    }
}
