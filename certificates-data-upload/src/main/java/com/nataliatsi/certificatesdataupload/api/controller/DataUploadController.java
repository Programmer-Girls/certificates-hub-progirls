package com.nataliatsi.certificatesdataupload.api.controller;

import com.nataliatsi.certificatesdataupload.api.core.service.UploadProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/uploads")
public class DataUploadController {

    private final UploadProcessingService uploadService;

    public DataUploadController(UploadProcessingService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/participants")
    public ResponseEntity<Void> uploadParticipantData(@RequestParam("file") MultipartFile file, @RequestParam("format") String format) {
        uploadService.processUpload(file, format);
        return ResponseEntity.accepted().build();
    }

}
