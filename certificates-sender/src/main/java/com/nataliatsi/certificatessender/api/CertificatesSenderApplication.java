package com.nataliatsi.certificatessender.api;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CertificatesSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CertificatesSenderApplication.class, args);
    }

}
