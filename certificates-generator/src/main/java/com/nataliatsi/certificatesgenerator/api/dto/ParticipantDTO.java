package com.nataliatsi.certificatesgenerator.api.dto;

public record ParticipantDTO(
        String name,
        String email,
        EventDTO event
) {
}
