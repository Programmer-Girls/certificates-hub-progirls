package com.nataliatsi.certificatesdataupload.api.core.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nataliatsi.certificatesdataupload.api.dto.ParticipantDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component("json")
public class JsonFileProcessor implements FileProcessorStrategy{
    private final ObjectMapper objectMapper;

    public JsonFileProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<ParticipantDTO> processFile(MultipartFile file) throws Exception {
        return Arrays.asList(objectMapper.readValue(file.getInputStream(), ParticipantDTO[].class));
    }
}
