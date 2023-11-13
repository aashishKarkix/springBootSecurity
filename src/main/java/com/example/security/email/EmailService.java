package com.example.security.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.ST;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Value("${mail.username}")
    private String fromEmail;

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationEmail(String toEmail, String verificationLink, String firstName, String lastName) {
        String templateContent = loadTemplateFromFile("templates/email/email-template.st");

        ST stringTemplate = new ST(templateContent, '<', '>');

        stringTemplate.add("firstName", firstName);
        stringTemplate.add("lastName", lastName);
        stringTemplate.add("verificationLink", verificationLink);

        String renderedContent = stringTemplate.render();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Account Verification");
        mailMessage.setText(renderedContent);
        javaMailSender.send(mailMessage);
    }

    private String loadTemplateFromFile(String path) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("Template file not found: " + path);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error loading template file: " + path, e);
        }
    }
}
