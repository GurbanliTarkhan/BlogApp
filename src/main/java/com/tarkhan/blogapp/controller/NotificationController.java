package com.tarkhan.blogapp.controller;

import com.tarkhan.blogapp.constants.Constants;
import com.tarkhan.blogapp.model.ResponseModel;
import com.tarkhan.blogapp.model.notification.NotificationDto;
import com.tarkhan.blogapp.model.notification.CreateNotificationDto;
import com.tarkhan.blogapp.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get Notifications by User ID")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationDto> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Notification by ID")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        NotificationDto notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }

    @PostMapping
    @Operation(summary = "Create Notification")
    public ResponseEntity<ResponseModel> createNotification(
            @Valid @RequestBody CreateNotificationDto createNotificationDto) {
        NotificationDto notification = notificationService.createNotification(createNotificationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseModel(
                        Constants.STATUS_201,
                        Constants.MESSAGE_201
                ));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Notification by ID")
    public ResponseEntity<ResponseModel> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ResponseModel(
                        Constants.STATUS_204,
                        Constants.MESSAGE_204
                ));
    }
}
