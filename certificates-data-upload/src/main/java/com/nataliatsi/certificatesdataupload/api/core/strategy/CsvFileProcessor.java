package com.nataliatsi.certificatesdataupload.api.core.strategy;

import com.nataliatsi.certificatesdataupload.api.dto.ParticipantDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component("csv")
public class CsvFileProcessor implements FileProcessorStrategy {

    @Override
    public List<ParticipantDTO> processFile(MultipartFile file) throws Exception {
        List<ParticipantDTO> participants = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IllegalArgumentException("CSV está vazio");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] columns = line.split(";");
                if (columns.length < 2) continue;

                String email = columns[0].trim();
                String name = columns[1].trim();

                participants.add(new ParticipantDTO(name, email));
            }
        }

        return participants;
    }
}