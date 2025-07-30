package com.nataliatsi.certificatesdataupload.api.dto;

public record EventDTO(
        String name,
        String description,
        String workload,
        String speaker,
        boolean remote
) {
}
