package com.nataliatsi.certificatesdataupload.api.dto;

import java.time.LocalDate;

public record EventDTO(
        String name,
        String description,
        int workload,
        String speaker,
        boolean remote,
        LocalDate date
) {
}
