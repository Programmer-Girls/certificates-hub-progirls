package com.nataliatsi.certificatesgenerator.core.service;

import com.nataliatsi.certificatesgenerator.api.dto.CertificateMessageDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.out.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.out.routing-key}")
    private String certificateRoutingKey;

    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(CertificateMessageDTO message) {
        rabbitTemplate.convertAndSend(exchange, certificateRoutingKey, message);
    }
}
