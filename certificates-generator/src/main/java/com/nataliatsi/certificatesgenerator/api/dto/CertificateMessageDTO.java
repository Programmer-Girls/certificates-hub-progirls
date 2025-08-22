package com.nataliatsi.certificatesgenerator.api.dto;

public record CertificateMessageDTO(
        String participantName,
        String participantEmail,
        String certificateLink
) {
}
