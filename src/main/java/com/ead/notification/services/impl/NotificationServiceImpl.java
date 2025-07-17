package com.ead.notification.services.impl;

import com.ead.notification.repositories.NotificationModelRepository;
import com.ead.notification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    final NotificationModelRepository notificationModelRepository;

}
