package com.nataliatsi.certificatesgenerator.core.service;

import com.nataliatsi.certificatesgenerator.api.dto.ParticipantDTO;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

@Component
public class PdfGenerator {

    private final TemplateEngine templateEngine;

    public PdfGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    private String buildHtml(ParticipantDTO participant) {
        Context context = new Context();
        context.setVariable("name", participant.name());
        context.setVariable("eventName", participant.event().name());
        context.setVariable("workload", participant.event().workload());

        return templateEngine.process("certificate-template", context);
    }

    private String prepareOutputPath(String participantName) {
        String fileName = participantName.replace(" ", "_") + "_certificate.pdf";
        String outputPath = Paths.get("certificates", fileName).toString();

        File directory = new File("certificates");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return outputPath;
    }

    private void generatePdf(String htmlContent, String outputPath) throws Exception {
        if (htmlContent == null || htmlContent.trim().isEmpty()) {
            throw new RuntimeException("Generated HTML is empty. Unable to create PDF.");
        }

        try (OutputStream os = new FileOutputStream(outputPath)) {
            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocumentFromString(htmlContent);
            renderer.getSharedContext().setReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory());
            renderer.getSharedContext().setBaseURL("file:./");
            renderer.layout();

            renderer.createPDF(os);
        }
    }

    public String generateCertificate(ParticipantDTO participant) {
        try {
            String htmlContent = buildHtml(participant);
            String outputPath = prepareOutputPath(participant.name());
            generatePdf(htmlContent, outputPath);
            return outputPath;
        } catch (Exception e) {
            throw new RuntimeException("Error generating certificate", e);
        }
    }
}