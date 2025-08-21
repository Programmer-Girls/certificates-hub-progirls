package com.nataliatsi.certificatesdataupload.api.core.strategy;

import com.nataliatsi.certificatesdataupload.api.dto.EventDTO;
import com.nataliatsi.certificatesdataupload.api.dto.ParticipantDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component("csv")
public class CsvFileProcessor implements FileProcessorStrategy{
    
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
                String[] columns = line.split(",");

                String name = columns[0].trim();
                String email = columns[1].trim();
                EventDTO event = getEventDTO(columns);
                participants.add(new ParticipantDTO(name, email, event));
            }
        }

        return participants;
    }

    private static EventDTO getEventDTO(String[] columns) {
        String eventName = columns[2].trim();
        String eventDescription = columns[3].trim();
        int workload = Integer.parseInt(columns[4].trim());
        String speaker = columns[5].trim();
        boolean remote = columns[6].trim().equalsIgnoreCase("sim") || columns[6].trim().equalsIgnoreCase("true");
        LocalDate date = LocalDate.parse(columns[7].trim());

        return new EventDTO(eventName, eventDescription, workload, speaker, remote, date);
    }
}
