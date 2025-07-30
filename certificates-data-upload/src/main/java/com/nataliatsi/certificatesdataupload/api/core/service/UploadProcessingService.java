package com.nataliatsi.certificatesdataupload.api.core.service;

import com.nataliatsi.certificatesdataupload.api.dto.ParticipantDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UploadProcessingService {

    private final ParticipantMappingService mappingService;
    private final ParticipantDispatchService dispatchService;

    public UploadProcessingService(ParticipantMappingService mappingService, ParticipantDispatchService dispatchService) {
        this.mappingService = mappingService;
        this.dispatchService = dispatchService;
    }

    public void processUpload(MultipartFile file, String format) {
        List<ParticipantDTO> participants = mappingService.processFile(file, format);
        dispatchService.sendAll(participants);
    }
}
