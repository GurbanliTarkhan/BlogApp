package com.tarkhan.blogapp.service;

import com.tarkhan.blogapp.entity.PhotoFile;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoFileService {
    Long uploadFile(MultipartFile file);
    byte[] downloadFile(String fileName);
    String deleteFile(String fileName);
    Page<PhotoFile> getAllPhotos(int size, int pageSize);
}
