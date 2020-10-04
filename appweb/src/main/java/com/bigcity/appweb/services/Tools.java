/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.services;

import com.bigcity.appweb.beans.ExceptionMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author nicolasdotnet
 */
public class Tools {
    
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(Tools.class);

    public static ExceptionMessage messageExtraction(HttpClientErrorException e){

        ObjectMapper mapper = new ObjectMapper();
        
        ExceptionMessage message = null ;

        try {
           message = mapper.readValue(e.getResponseBodyAsString(), ExceptionMessage.class);
        } catch (JsonProcessingException ex) {
            log.error("erreur de parse json !");
        }
        return message;

    }

}
