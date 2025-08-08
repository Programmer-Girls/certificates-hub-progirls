package com.nataliatsi.certificatesdataupload.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

public record ParticipantUploadRequest(

        @Schema(description = "The participant data file", type = "string", format = "binary", required = true)
        MultipartFile file,

        @Schema(description = "The format of the file (e.g., csv, json)", required = true, example = "json")
        String format
) {
}
