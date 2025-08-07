package com.nataliatsi.certificatesdataupload.api.dto;

import java.time.LocalDateTime;

public record SuccessResponseDTO(
        LocalDateTime timestamp,
        int status,
        String message
) {
    public SuccessResponseDTO(int status, String message) {
        this(LocalDateTime.now(), status, message);
    }
}
