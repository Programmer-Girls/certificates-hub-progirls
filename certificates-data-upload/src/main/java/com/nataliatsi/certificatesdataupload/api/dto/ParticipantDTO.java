package com.nataliatsi.certificatesdataupload.api.dto;

public record ParticipantDTO(
        String name,
        String email,
        EventDTO event
) {
}
