package com.nataliatsi.certificatessender.api.service;

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

    public void enviarCertificado(String destinatario, String nomeParticipante, String assunto, String link) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            context.setVariable("nome", nomeParticipante);
            context.setVariable("linkCertificado", link);
            String html = templateEngine.process("certificado-email", context);

            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(html, true);
            helper.setFrom("no-reply@certificados.com");

            mailSender.send(mimeMessage);
            System.out.println("Email enviado com sucesso para " + destinatario);

        } catch (MessagingException e) {
            System.out.println("Erro ao enviar certificado: " + e.getMessage());
        }
    }
}
