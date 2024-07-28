package com.tarkhan.blogapp.model.report;

import lombok.Data;

@Data
public class CreateReportDto {
    private String reason;
    private String description;
    private Long reportedById;
    private Long postId;
}
