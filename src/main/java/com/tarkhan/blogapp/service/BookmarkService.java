package com.tarkhan.blogapp.service;

import com.tarkhan.blogapp.model.bookmark.BookmarkDto;
import com.tarkhan.blogapp.model.bookmark.CreateBookmarkDto;

import java.util.List;

public interface BookmarkService {
    List<BookmarkDto> getAllBookmarks();
    BookmarkDto getBookmarkById(Long id);
    BookmarkDto createBookmark(CreateBookmarkDto createBookmarkDto);
    void deleteBookmark(Long id);
}
