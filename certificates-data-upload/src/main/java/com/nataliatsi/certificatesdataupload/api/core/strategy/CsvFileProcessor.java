package com.nataliatsi.certificatesdataupload.api.core.strategy;

import org.springframework.stereotype.Component;

@Component("csv")
public class CsvFileProcessor {
//    @Override
//    public List<ParticipantRequestDTO> processFile(MultipartFile file) throws Exception {
//        List<ParticipantRequestDTO> participants = new ArrayList<>();
//
//        try(CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
//            String[] line;
//            reader.skip(1);
//            while ((line = reader.readNext()) != null) {
//                String name = line[0];
//                String email = line[1];
//                String eventName = line[2];
//                String eventDate = line[3];
//                String speaker = line[4];
//                String workload = line[5];
//                boolean remote = Boolean.parseBoolean(line[6]);
//
//                EventDTO event = new EventDTO(eventName, eventDate, speaker, workload, remote);
//                ParticipantRequestDTO participant = new ParticipantRequestDTO(name, email, event);
//                participants.add(participant);
//            }
//        }
//        return participants;
//    }
}
