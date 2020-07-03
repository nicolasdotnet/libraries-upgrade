/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.services;

import com.bigcity.beans.Revive;
import com.bigcity.services.interfaces.IEmailService;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 *
 * @author nicolasdotnet
 */
@Service
public class EmailServiceImpl implements IEmailService {

    private static final String NOREPLY_ADDRESS = "noreply@baeldung.com";

    @Value("${reviveSubject}")
    private String reviveSubject;

    @Value("${senderName}")
    private String senderName;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

//    @Value("classpath:/mail-logo.png")
//    private Resource resourceFile;
    @Override
    public void sendReviveMessage(Revive revive)
            throws MessagingException {

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", revive.getFirstname());
        templateModel.put("title", revive.getBookTitle());
        templateModel.put("bookingEndDate", revive.getBookingEndDate());
        templateModel.put("senderName", senderName);

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);

        String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);

        sendHtmlMessage(revive.getEmail(), reviveSubject, htmlBody);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
//        helper.addInline("attachment.png", resourceFile);
        emailSender.send(message);
    }

}
