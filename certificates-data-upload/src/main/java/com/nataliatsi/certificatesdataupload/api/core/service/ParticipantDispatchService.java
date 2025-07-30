package com.nataliatsi.certificatesdataupload.api.core.service;

import com.nataliatsi.certificatesdataupload.api.dto.ParticipantDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantDispatchService {

    private final RabbitTemplate rabbitTemplate;

    private String exchangeName;

    private String routingKey;

    public ParticipantDispatchService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToGenerationQueue(ParticipantDTO participantDTO) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, participantDTO);
    }

    public void sendAll(List<ParticipantDTO> participants) {
        for (ParticipantDTO p : participants) {
            sendToGenerationQueue(p);
        }
    }
}
