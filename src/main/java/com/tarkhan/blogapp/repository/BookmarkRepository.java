package com.tarkhan.blogapp.repository;

import com.tarkhan.blogapp.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
