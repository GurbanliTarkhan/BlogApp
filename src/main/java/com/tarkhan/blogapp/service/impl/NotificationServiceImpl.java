package com.tarkhan.blogapp.service.impl;

import com.tarkhan.blogapp.entity.Notification;
import com.tarkhan.blogapp.entity.User;
import com.tarkhan.blogapp.exception.ResourceNotFoundException;
import com.tarkhan.blogapp.model.notification.NotificationDto;
import com.tarkhan.blogapp.model.notification.CreateNotificationDto;
import com.tarkhan.blogapp.repository.NotificationRepository;
import com.tarkhan.blogapp.repository.UserRepository;
import com.tarkhan.blogapp.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<NotificationDto> getNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDto getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "ID", id));
        return modelMapper.map(notification, NotificationDto.class);
    }

    @Override
    public NotificationDto createNotification(CreateNotificationDto createNotificationDto) {
        User user = userRepository.findById(createNotificationDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", createNotificationDto.getUserId()));

        Notification notification = modelMapper.map(createNotificationDto, Notification.class);
        notification.setUser(user);
        notification.setCreatedAt(LocalDateTime.now());

        notification = notificationRepository.save(notification);
        return modelMapper.map(notification, NotificationDto.class);
    }

    @Override
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "ID", id));
        notificationRepository.delete(notification);
    }
}
