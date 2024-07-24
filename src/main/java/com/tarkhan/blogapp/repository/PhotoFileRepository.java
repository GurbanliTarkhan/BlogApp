package com.tarkhan.blogapp.repository;

import com.tarkhan.blogapp.entity.PhotoFile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoFileRepository extends JpaRepository<PhotoFile, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM PhotoFile f WHERE f.path = :path")
    void deleteByPath(@Param("path") String path);
}
