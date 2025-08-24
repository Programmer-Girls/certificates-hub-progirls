package com.nataliatsi.certificatessender.api.dto;

public record MessageDTO(
        String participantName,
        String participantEmail,
        String certificateLink
) {
}