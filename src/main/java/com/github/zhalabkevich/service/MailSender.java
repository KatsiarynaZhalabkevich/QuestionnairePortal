package com.github.zhalabkevich.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.Serializable;


@Service
public class MailSender implements Serializable {
    @Value("${spring.mail.username}")
    private String username;


    @Autowired
    private JavaMailSender mailSender;


    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setTo(emailTo);
        mailSender.send(mailMessage);
    }


}
