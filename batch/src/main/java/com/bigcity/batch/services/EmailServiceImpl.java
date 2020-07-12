/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.services;

import com.bigcity.batch.services.interfaces.IEmailService;
import java.io.File;
import javax.mail.MessagingException;

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author nicolasdotnet
 */
@Service
public class EmailServiceImpl implements IEmailService {

    private static final String NOREPLY_ADDRESS = "noreply@biblio.com";

    @Autowired
    private JavaMailSender emailSender;

    @Value("classpath:/mail-logo.png")
    Resource resourceFile;

    @Override
    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {

//        logger.info("sendHtmlMessage() {}", to);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.addInline("logo.png", resourceFile);
        emailSender.send(message);
    }

    @Override
    public void sendHtmlMessage(String to, String subject, String htmlBody, String pathToAttachment) throws MessagingException{

//        logger.info("sendHtmlMessage() {}", to);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.addInline("logo.png", resourceFile);

        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("fileToAttachment", file);

        emailSender.send(message);
    }

}
