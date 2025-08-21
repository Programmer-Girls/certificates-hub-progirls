package com.nataliatsi.certificatessender.api.service;

import com.nataliatsi.certificatessender.api.dto.MessageDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final EmailService emailService;

    public MessageConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void receiveMessage(@Payload MessageDTO dto) {
        emailService.sendCertificate(dto);
    }
}
