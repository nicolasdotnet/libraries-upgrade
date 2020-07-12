/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.services.interfaces;

import javax.mail.MessagingException;

/**
 *
 * @author nicolasdotnet
 */
public interface IEmailService {

    void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException;
    
    void sendHtmlMessage(String to, String subject, String htmlBody, String pathToAttachment) throws MessagingException;

}
