package com.tarkhan.blogapp.model.notification;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;
    private String type;
    private String message;
    private LocalDateTime createdAt;
    private Long userId;
}
