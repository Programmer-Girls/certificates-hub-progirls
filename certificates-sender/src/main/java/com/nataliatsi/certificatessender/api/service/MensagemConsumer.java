package com.nataliatsi.certificatessender.api.service;

import com.nataliatsi.certificatessender.api.dto.MensagemDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MensagemConsumer {

    private final EmailService emailService;

    public MensagemConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void receberMensagem(@Payload MensagemDTO dto) {
        System.out.println("Mensagem recebida na fila: ");
        System.out.println(dto);
        emailService.enviarCertificado(dto.email(), dto.nome(), dto.assunto(), dto.link());
    }
}
