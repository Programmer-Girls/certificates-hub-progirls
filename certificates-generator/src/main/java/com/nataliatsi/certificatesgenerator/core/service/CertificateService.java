package com.nataliatsi.certificatesgenerator.core.service;

import com.nataliatsi.certificatesgenerator.api.dto.ParticipantDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CertificateService {

    private final PdfGenerator pdfGenerator;
    private final RestTemplate restTemplate = new RestTemplate();

    public CertificateService(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }

    public void processCertificate(ParticipantDTO participant) {
        String url = "http://ms3:8083/api/certificates/send-email";

        String path = pdfGenerator.generateCertificate(participant);

        byte[] pdfBytes = pdfGenerator.generateCertificateBytes(participant);
        ByteArrayResource pdfResource = new ByteArrayResource(pdfBytes) {
            public String getFilename() {
                return participant.name().replace(" ", "_") + "_ProGirlsTechWeek.pdf";
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", participant.name());
        body.add("email", participant.email());
        body.add("certificate", pdfResource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        // System.out.println("Response from ms3: " + response.getStatusCode() + " - " + response.getBody());
    }
}
