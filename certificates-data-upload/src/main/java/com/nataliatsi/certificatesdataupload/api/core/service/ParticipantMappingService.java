package com.nataliatsi.certificatesdataupload.api.core.service;

import com.nataliatsi.certificatesdataupload.api.core.exception.InvalidFormatException;
import com.nataliatsi.certificatesdataupload.api.core.exception.UnsupportedFormatException;
import com.nataliatsi.certificatesdataupload.api.core.strategy.FileProcessorStrategy;
import com.nataliatsi.certificatesdataupload.api.dto.ParticipantDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class ParticipantMappingService {

    private final Map<String, FileProcessorStrategy> strategyMap;

    public ParticipantMappingService(Map<String, FileProcessorStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public List<ParticipantDTO> processFile(MultipartFile file, String format) {
        if (file == null || file.isEmpty()) {
            throw new InvalidFormatException("File is null or empty");
        }

        if (format == null || format.isEmpty()) {
            throw new InvalidFormatException("The file format was not specified.");
        }

        FileProcessorStrategy strategy = strategyMap.get(format.toLowerCase());
        if (strategy == null) {
            throw new UnsupportedFormatException("File format '" + format + "' is not supported.");
        }

        try {
            return strategy.processFile(file);
        } catch (Exception e) {
            throw new InvalidFormatException("Error processing file: " + e.getMessage());
        }
    }
}
