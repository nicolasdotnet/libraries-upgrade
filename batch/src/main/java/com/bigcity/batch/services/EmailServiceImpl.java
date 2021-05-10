/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.services;

import com.bigcity.batch.services.interfaces.IEmailService;
import java.io.File;
import java.nio.charset.Charset;
import javax.mail.MessagingException;

import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
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

    private final org.apache.logging.log4j.Logger log = LogManager.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;

    @Value("classpath:/mail-logo.png")
    private Resource resourceFile;

    @Value("noreply_address")
    private String noreplyAddress;

    @Override
    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {

        log.debug("sendHtmlMessage() to: subject: htmlBody: {}");

        String formattedSubject = new String(subject.getBytes(), Charset.forName("UTF-8"));

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(noreplyAddress);
        helper.setTo(to);
        helper.setSubject(formattedSubject);
        helper.setText(htmlBody, true);
        helper.addInline("logo.png", resourceFile);
        emailSender.send(message);
    }

    @Override
    public void sendHtmlMessage(String to, String subject, String htmlBody, String pathToAttachment) throws MessagingException {

        log.debug("sendHtmlMessage() to: subject: htmlBody: pathToAttachment: {}");
        
        String formattedSubject = new String(subject.getBytes(), Charset.forName("UTF-8"));

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(noreplyAddress);
        helper.setTo(to);
        helper.setSubject(formattedSubject);
        helper.setText(htmlBody, true);
        helper.addInline("logo.png", resourceFile);

        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("fileToAttachment", file);

        emailSender.send(message);
    }

}
