package com.nataliatsi.certificatesdataupload.api.core.strategy;

import com.nataliatsi.certificatesdataupload.api.dto.ParticipantDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileProcessorStrategy {
    List<ParticipantDTO> processFile(MultipartFile file) throws Exception;
}
