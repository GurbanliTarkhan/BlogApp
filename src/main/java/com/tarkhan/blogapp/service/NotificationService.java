package com.tarkhan.blogapp.service;

import com.tarkhan.blogapp.model.notification.CreateNotificationDto;
import com.tarkhan.blogapp.model.notification.NotificationDto;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getNotificationsByUserId(Long userId);
    NotificationDto getNotificationById(Long id);
    NotificationDto createNotification(CreateNotificationDto createNotificationDto);
    void deleteNotification(Long id);
}
