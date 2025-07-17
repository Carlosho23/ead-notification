package com.ead.notification.services.impl;

import com.ead.notification.dtos.NotificationRecordCommandDto;
import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.repositories.NotificationModelRepository;
import com.ead.notification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
}
