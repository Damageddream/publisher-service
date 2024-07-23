package com.dd.publisher.service;

import com.dd.publisher.Notification;
import com.dd.publisher.model.Student;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {
    public static final String URS_STUDENT_SERVICE = "http://localhost:8080/students/";
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    public NotificationServiceImpl(RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendStudentNotification(Long studentId) {
        Student student = restTemplate.exchange(URS_STUDENT_SERVICE + studentId,
                HttpMethod.GET, HttpEntity.EMPTY, Student.class).getBody();
        Notification notification = createNotification(student);

        rabbitTemplate.convertAndSend("kurs", notification);
    }

    private  Notification createNotification(Student student) {
        Notification notification = new Notification();
        notification.setEmail(student.getEmail());
        notification.setTitle("Witaj! "+ student.getFirstName());
        notification.setBody("Miło, że jestes "+ student.getFirstName()+ " "+ student.getLastName());
        return notification;
    }
}
