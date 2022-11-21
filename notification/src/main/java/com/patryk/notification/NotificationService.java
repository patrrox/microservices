package com.patryk.notification;


import com.patryk.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationJpaRepository repository;

    public Notification send(NotificationRequest notificationRequest) {
        return repository.save(Notification
                .builder()
                .toCustomerId(notificationRequest.toCustomerId())
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .sender("Patryk")
                .message(notificationRequest.message())
                .sentAt(LocalDateTime.now())
                .build());
    }


}
