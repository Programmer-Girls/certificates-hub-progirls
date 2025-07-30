package com.nataliatsi.certificatesgenerator.api.controller;

import com.nataliatsi.certificatesgenerator.api.dto.MensagemDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mensagens")
class MensagemController {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    MensagemController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public String enviarMensagem(@RequestBody MensagemDTO mensagem) {
        rabbitTemplate.convertAndSend(exchange, routingKey, mensagem);
        return "Mensagem enviada para a fila com sucesso!";
    }

}
