package com.nataliatsi.certificatesgenerator.core.service;

import com.nataliatsi.certificatesgenerator.api.dto.CertificateMessageDTO;
import com.nataliatsi.certificatesgenerator.api.dto.ParticipantDTO;
import org.springframework.stereotype.Service;

@Service
public class CertificateService {

    private final PdfGenerator pdfGenerator;
    private final Publisher publisher;

    public CertificateService(PdfGenerator pdfGenerator, Publisher publisher) {
        this.pdfGenerator = pdfGenerator;
        this.publisher = publisher;
    }

    public void processCertificate(ParticipantDTO participant) {
        String pdfPath = pdfGenerator.generateCertificate(participant);
        CertificateMessageDTO message = new CertificateMessageDTO(participant.name(), participant.email(), pdfPath);

        publisher.publish(message);
    }
}
