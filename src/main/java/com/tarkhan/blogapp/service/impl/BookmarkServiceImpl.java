package com.tarkhan.blogapp.service.impl;

import com.tarkhan.blogapp.entity.Bookmark;
import com.tarkhan.blogapp.entity.Post;
import com.tarkhan.blogapp.entity.User;
import com.tarkhan.blogapp.exception.ResourceNotFoundException;
import com.tarkhan.blogapp.model.bookmark.BookmarkDto;
import com.tarkhan.blogapp.model.bookmark.CreateBookmarkDto;
import com.tarkhan.blogapp.repository.BookmarkRepository;
import com.tarkhan.blogapp.repository.PostRepository;
import com.tarkhan.blogapp.repository.UserRepository;
import com.tarkhan.blogapp.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<BookmarkDto> getAllBookmarks() {
        return bookmarkRepository.findAll().stream()
                .map(bookmark -> modelMapper.map(bookmark, BookmarkDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookmarkDto getBookmarkById(Long id) {
        Bookmark bookmark = bookmarkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark", "ID", id));
        return modelMapper.map(bookmark, BookmarkDto.class);
    }

    @Override
    public BookmarkDto createBookmark(CreateBookmarkDto createBookmarkDto) {
        Bookmark bookmark = modelMapper.map(createBookmarkDto, Bookmark.class);

        User user = userRepository.findById(createBookmarkDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", createBookmarkDto.getUserId()));
        Post post = postRepository.findById(createBookmarkDto.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", createBookmarkDto.getPostId()));

        bookmark.setUser(user);
        bookmark.setPost(post);

        bookmark = bookmarkRepository.save(bookmark);
        return modelMapper.map(bookmark, BookmarkDto.class);
    }

    @Override
    public void deleteBookmark(Long id) {
        Bookmark bookmark = bookmarkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark", "ID", id));
        bookmarkRepository.delete(bookmark);
    }
}
