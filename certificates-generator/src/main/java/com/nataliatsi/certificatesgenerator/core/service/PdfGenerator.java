package com.nataliatsi.certificatesgenerator.core.service;

import com.lowagie.text.pdf.BaseFont;
import com.nataliatsi.certificatesgenerator.api.dto.ParticipantDTO;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class PdfGenerator {

    private final TemplateEngine templateEngine;

    public PdfGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    private String buildHtml(ParticipantDTO participant) {
        Context context = new Context();
        context.setVariable("name", participant.name());

        return templateEngine.process("certificate-template", context);
    }

    private String prepareOutputPath(String participantName) {
        String fileName = participantName.replace(" ", "_") + "_ProGirlsTechWeek.pdf";
        String outputPath = Paths.get("certificates", fileName).toString();

        File directory = new File("certificates");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return outputPath;
    }

    private void renderPdf(String htmlContent, OutputStream os) throws Exception {
        if (htmlContent == null || htmlContent.trim().isEmpty()) {
            throw new RuntimeException("Generated HTML is empty. Unable to create PDF.");
        }

        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();

        fontResolver.addFont(
                Objects.requireNonNull(this.getClass().getResource("/fonts/Poppins-Regular.ttf")).toExternalForm(),
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );
        fontResolver.addFont(
                Objects.requireNonNull(this.getClass().getResource("/fonts/Poppins-Bold.ttf")).toExternalForm(),
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );

        renderer.setDocumentFromString(htmlContent);
        renderer.getSharedContext().setBaseURL("file:./");
        renderer.layout();
        renderer.createPDF(os);
    }

    public String generateCertificate(ParticipantDTO participant) {
        try {
            String htmlContent = buildHtml(participant);
            String outputPath = prepareOutputPath(participant.name());
            try (OutputStream os = new FileOutputStream(outputPath)) {
                renderPdf(htmlContent, os);
            }
            return outputPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating certificates", e);
        }
    }

    public byte[] generateCertificateBytes(ParticipantDTO participant) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            String htmlContent = buildHtml(participant);
            renderPdf(htmlContent, os);
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating certificates", e);
        }
    }
}