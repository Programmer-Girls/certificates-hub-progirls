package com.nataliatsi.certificatessender.api.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Objects;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendCertificate(String name, String email, MultipartFile certificate) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            context.setVariable("name", name);

            String html = templateEngine.process("certificate-email", context);

            helper.setTo(email);
            helper.setSubject("Seu certificado da Tech Week está pronto!");
            helper.setText(html, true);

            if (certificate != null && !certificate.isEmpty()) {
                helper.addAttachment(Objects.requireNonNull(certificate.getOriginalFilename()), certificate);
            }

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            System.out.println("Error while sending certificate: " + e.getMessage());
        }
    }
}
