package com.nataliatsi.certificatesgenerator.infrastructure.messaging;

import com.nataliatsi.certificatesgenerator.api.dto.ParticipantDTO;
import com.nataliatsi.certificatesgenerator.core.service.CertificateService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ParticipantConsumer {
    private final CertificateService service;

    public ParticipantConsumer(CertificateService service) {
        this.service = service;
    }

    @RabbitListener(queues = "certificates.participant.queue")
    public void consume(ParticipantDTO participant) {
        service.processCertificate(participant);
    }
}
