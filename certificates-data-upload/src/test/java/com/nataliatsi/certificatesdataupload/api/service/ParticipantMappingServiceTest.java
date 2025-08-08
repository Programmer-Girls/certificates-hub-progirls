package com.nataliatsi.certificatesdataupload.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nataliatsi.certificatesdataupload.api.core.exception.InvalidFormatException;
import com.nataliatsi.certificatesdataupload.api.core.exception.UnsupportedFormatException;
import com.nataliatsi.certificatesdataupload.api.core.service.ParticipantMappingService;
import com.nataliatsi.certificatesdataupload.api.core.strategy.FileProcessorStrategy;
import com.nataliatsi.certificatesdataupload.api.core.strategy.JsonFileProcessor;
import com.nataliatsi.certificatesdataupload.api.dto.ParticipantDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParticipantMappingServiceTest {

    private ParticipantMappingService service;

    @BeforeEach
    void setUp() {
        Map<String, FileProcessorStrategy> strategyMap = new HashMap<>();
        strategyMap.put("json", mock(FileProcessorStrategy.class));
        service = new ParticipantMappingService(strategyMap);
    }

    @Test
    void shouldThrowInvalidFormatException_whenFileIsNull() {
        InvalidFormatException exception = assertThrows(
                InvalidFormatException.class,
                () -> service.processFile(null, "json")
        );

        assertEquals("File is null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidFormatException_whenFileIsEmpty() {
        MultipartFile fileMock = mock(MultipartFile.class);
        when(fileMock.isEmpty()).thenReturn(true);

        InvalidFormatException exception = assertThrows(
                InvalidFormatException.class,
                () -> service.processFile(fileMock, "json")
        );

        assertEquals("File is null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidFormatException_whenFormatIsNull() {
        MultipartFile fileMock = mock(MultipartFile.class);
        when(fileMock.isEmpty()).thenReturn(false);

        InvalidFormatException exception = assertThrows(InvalidFormatException.class, () -> service.processFile(fileMock, null));

        assertEquals("The file format was not specified.", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidFormatException_whenFormatIsEmpty() {
        MultipartFile fileMock = mock(MultipartFile.class);
        when(fileMock.isEmpty()).thenReturn(false);

        String format = "";

        InvalidFormatException exception = assertThrows(InvalidFormatException.class, () -> service.processFile(fileMock, format));

        assertEquals("The file format was not specified.", exception.getMessage());
    }

    @Test
    void shouldThrowUnsupportedFormatException_whenStrategyIsNotFoundForFormat() {
        MultipartFile fileMock = mock(MultipartFile.class);
        when(fileMock.isEmpty()).thenReturn(false);

        String format = "unknown";

        UnsupportedFormatException exception = assertThrows(UnsupportedFormatException.class, () -> service.processFile(fileMock, format));

        assertEquals("File format '" + format + "' is not supported.", exception.getMessage());
    }

    @Test
    void shouldParseParticipantsSuccessfully_whenValidFileIsProvided() throws Exception {
        ClassPathResource resource = new ClassPathResource("json/participants.json");
        byte[] jsonBytes = resource.getInputStream().readAllBytes();

        MultipartFile file = new MockMultipartFile("file",
                "participants-detailed.json",
                "application/json",
                jsonBytes);

        ObjectMapper mapper = new ObjectMapper();
        JsonFileProcessor jsonFileProcessor = new JsonFileProcessor(mapper);

        List<ParticipantDTO> participants = jsonFileProcessor.processFile(file);

        assertNotNull(participants);
        assertEquals(3, participants.size());

        ParticipantDTO first = participants.get(0);
        assertEquals("Tanjiro Kamado", first.name());
        assertEquals("tanjiro.kamado@demoncorp.org", first.email());
        assertNotNull(first.event());
        assertEquals("Técnicas de Respiração: Estilo da Água", first.event().name());
    }

    @Test
    void shouldThrowInvalidFormatException_whenStrategyFailsToProcessFile() throws Exception {
        MultipartFile fileMock = mock(MultipartFile.class);
        when(fileMock.isEmpty()).thenReturn(false);

        FileProcessorStrategy failingStrategy = mock(FileProcessorStrategy.class);
        when(failingStrategy.processFile(fileMock)).thenThrow(new RuntimeException("Simulated processing error"));

        Map<String, FileProcessorStrategy> strategyMap = new HashMap<>();
        strategyMap.put("json", failingStrategy);
        ParticipantMappingService service = new ParticipantMappingService(strategyMap);

        InvalidFormatException exception = assertThrows(InvalidFormatException.class, () -> service.processFile(fileMock, "json"));

        assertEquals("Error processing file: Simulated processing error", exception.getMessage());
    }
}
