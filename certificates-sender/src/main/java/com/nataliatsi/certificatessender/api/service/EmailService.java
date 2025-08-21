package com.nataliatsi.certificatessender.api.service;

import com.nataliatsi.certificatessender.api.dto.MessageDTO;
import com.nataliatsi.certificatessender.api.util.ValidityDateHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendCertificate(MessageDTO messageDTO) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            context.setVariable("name", messageDTO.name());
            context.setVariable("certificateLink", messageDTO.certificateLink());
            context.setVariable("validityDate", ValidityDateHelper.calculateValidity(5));
            String html = templateEngine.process("certificate-email", context);

            helper.setTo(messageDTO.email());
            helper.setSubject(messageDTO.subject());
            helper.setText(html, true);
            helper.setFrom("no-reply@certificates.com");

            mailSender.send(mimeMessage);
            System.out.println("Email successfully sent to " + messageDTO.email());

        } catch (MessagingException e) {
            System.out.println("Error while sending certificate: " + e.getMessage());
        }
    }
}
