package com.tarkhan.blogapp.model.notification;

import lombok.Data;

@Data
public class CreateNotificationDto {
    private String type;
    private String message;
    private Long userId;
}
