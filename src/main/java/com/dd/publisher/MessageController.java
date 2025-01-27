package com.dd.publisher;

import com.dd.publisher.service.NotificationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

   private final NotificationService notificationService;

    public MessageController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @GetMapping("/notifications")
    public String sendStudentNotification(@RequestParam Long studentId){
        notificationService.sendStudentNotification(studentId);
        return "Wiadomość została wysłana do studenta o id: "+studentId;
    }
}
