package com.tarkhan.blogapp.model.report;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {
    private Long id;
    private String reason;
    private String description;
    private Long reportedById;
    private Long postId;
    private LocalDateTime createdAt;
}
