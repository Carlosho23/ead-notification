package com.ead.notification.controllers;

import com.ead.notification.configs.security.AuthenticationCurrentUserService;
import com.ead.notification.configs.security.UserDetailsImpl;
import com.ead.notification.dtos.NotificationRecordDto;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserNotificationController {

    final NotificationService notificationService;
    final AuthenticationCurrentUserService authenticationCurrentUserService;


    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> getAllNotificationsByUser(@PathVariable(value = "userId") UUID userId,
                                                                             Pageable pageable) {
        UserDetailsImpl userDetails = authenticationCurrentUserService.getCurrentUser();
        if (userDetails.getUserId().equals(userId) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.OK).body(notificationService.findAllNotificationsByUserId(userId, pageable));
        } else {
            throw new AccessDeniedException("Forbidden");
        }

    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable(value = "userId") UUID userId,
                                                     @PathVariable(value = "notificationId") UUID notificationId,
                                                     NotificationRecordDto notificationRecordDto) {
        UserDetailsImpl userDetails = authenticationCurrentUserService.getCurrentUser();
        if (userDetails.getUserId().equals(userId) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.OK).body(notificationService.updateNotification(notificationRecordDto, notificationService.findByNotificationIdAndUserId(notificationId, userId).get()));
        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }
}
