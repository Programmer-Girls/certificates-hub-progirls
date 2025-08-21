package com.nataliatsi.certificatessender.api.dto;

public record MessageDTO(
        String name, String email, String subject, String certificateLink
) {
}