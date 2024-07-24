package com.tarkhan.blogapp.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.tarkhan.blogapp.entity.PhotoFile;
import com.tarkhan.blogapp.exception.BlogApiException;
import com.tarkhan.blogapp.repository.PhotoFileRepository;
import com.tarkhan.blogapp.service.PhotoFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PhotoFileServiceImpl implements PhotoFileService {

    private final AmazonS3 s3Client;

    private final PhotoFileRepository fileRepository;

    @Value("${application.bucket.name}")
    private String bucketName;


    @Override
    public Long uploadFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            throw new BlogApiException("Only JPG and PNG files can be uploaded.");
        }
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();

        PhotoFile newFile = PhotoFile.builder()
                .name(file.getOriginalFilename())
                .path(fileName)
                .extensions(PhotoFile.getFileExtension(fileName))
                .size(file.getSize())
                .build();

        PhotoFile savedFile = fileRepository.save(newFile);
        return savedFile.getId();
    }

    @Override
    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        fileRepository.deleteByPath(fileName);
        return "File deleted : " + fileName;
    }

    @Override
    public Page<PhotoFile> getAllPhotos(int size, int pageSize) {
        Pageable pageable = PageRequest.of(size, pageSize);
        Page<PhotoFile> photoFiles = fileRepository.findAll(pageable);
        return photoFiles;
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }catch (IOException e) {
            throw new BlogApiException("Failed to convert multipart file to file");
        }
        return convertedFile;
    }

}
