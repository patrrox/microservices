package com.patryk.notification;


import com.patryk.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> sendNotification(@RequestBody NotificationRequest notificationRequest){
        log.info("New notification.. {}",notificationRequest);
        Notification savedNotification = notificationService.send(notificationRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + savedNotification.getNotificationId().toString()).build().toUri();
        return ResponseEntity.created(uri).build();
    }

}
