package com.nataliatsi.certificatesgenerator.core.service;

import org.springframework.stereotype.Service;

@Service
public class MessagingService {
    public void sendCertificate(String email, byte[] pdfBytes) {
        System.out.println("Simulando envio de e-mail para: " + email);
        System.out.println("Tamanho do PDF enviado: " + pdfBytes.length + " bytes");
    }
}
