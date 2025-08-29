package com.nataliatsi.certificatessender.api.controller;

import com.nataliatsi.certificatessender.api.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/certificates/")
public class CertificateController {

    private final EmailService emailService;

    public CertificateController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/send-email", consumes = "multipart/form-data")
    public ResponseEntity<String> sendEmail(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("certificate")MultipartFile certificate
            ) {
        emailService.sendCertificate(name, email, certificate);
        return ResponseEntity.ok("E-mail enviado com sucesso para: " + email);
    }
}
