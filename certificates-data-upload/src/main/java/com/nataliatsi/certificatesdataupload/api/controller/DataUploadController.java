package com.nataliatsi.certificatesdataupload.api.controller;

import com.nataliatsi.certificatesdataupload.api.core.service.UploadProcessingService;
import com.nataliatsi.certificatesdataupload.api.dto.ParticipantUploadRequest;
import com.nataliatsi.certificatesdataupload.api.dto.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/uploads")
public class DataUploadController {

    private final UploadProcessingService uploadService;

    public DataUploadController(UploadProcessingService uploadService) {
        this.uploadService = uploadService;
    }

    @Operation(
            summary = "Upload participant data file",
            description = "Accepts a participant data file and its format for asynchronous processing."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "File accepted for processing"),
            @ApiResponse(responseCode = "400", description = "Invalid request or missing parameters"),
            @ApiResponse(responseCode = "415", description = "Unsupported media type"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @PostMapping(value = "/participants", consumes = "multipart/form-data")
    public ResponseEntity<SuccessResponseDTO> uploadParticipantData(
            @ModelAttribute ParticipantUploadRequest request
    ) {
        uploadService.processUpload(request.file(), request.format());
        SuccessResponseDTO response = new SuccessResponseDTO(
                HttpStatus.ACCEPTED.value(),
                "Upload received and is being processed."
        );
        return ResponseEntity.accepted().body(response);
    }
}
