package com.nataliatsi.certificatesdataupload.api.dto;

public record EventDTO(
        String name,
        String description,
        int workload,
        String speaker,
        boolean remote
) {
}
