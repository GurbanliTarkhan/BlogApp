package com.tarkhan.blogapp.model.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPostDto {
    private String title;
    private String content;
    @Schema(type = "string", format = "binary", description = "Photo file")
    private MultipartFile photoFile;
    private Long userId;
    private List<Long> tagIds;
    private Long categoryId;
}
