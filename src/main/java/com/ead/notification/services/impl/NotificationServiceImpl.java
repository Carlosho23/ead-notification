package com.ead.notification.services.impl;

import com.ead.notification.dtos.NotificationRecordCommandDto;
import com.ead.notification.dtos.NotificationRecordDto;
import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.exceptions.NotFoundException;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.repositories.NotificationModelRepository;
import com.ead.notification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    final NotificationModelRepository notificationModelRepository;

    @Override
    public NotificationModel saveNotification(NotificationRecordCommandDto notificationRecordCommandDto) {
        NotificationModel notificationModel = new NotificationModel();
        BeanUtils.copyProperties(notificationRecordCommandDto, notificationModel);
        notificationModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        notificationModel.setNotificationStatus(NotificationStatus.CREATED);
        return notificationModelRepository.save(notificationModel);
    }

    @Override
    public Page<NotificationModel> findAllNotificationsByUserId(UUID userId, Pageable pageable) {
        return notificationModelRepository.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageable);
    }

    @Override
    public Optional<NotificationModel> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        Optional<NotificationModel> notificationModelOptional = notificationModelRepository.findByNotificationIdAndUserId(notificationId, userId);
        if (notificationModelOptional.isEmpty()) {
            throw new NotFoundException("Error: Notification for this user not found.");
        }
        return notificationModelOptional;
    }

    @Override
    public NotificationModel updateNotification(NotificationRecordDto notificationRecordDto, NotificationModel notificationModel) {
        notificationModel.setNotificationStatus(notificationRecordDto.notificationStatus());
        return notificationModelRepository.save(notificationModel);
    }


}
