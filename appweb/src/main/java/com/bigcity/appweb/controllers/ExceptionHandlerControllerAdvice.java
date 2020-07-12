/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.controllers;


import com.bigcity.appweb.beans.ExceptionMessage;
import com.bigcity.appweb.exception.DisplayException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author nicolasdotnet
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(DisplayException.class)
    public ExceptionMessage handleRestClientException(HttpServletRequest request, DisplayException e) {
        // log exception 

        ExceptionMessage message = ExceptionMessage.builder()
                .message(e.getMessage())
                .build();

        return message;
    }


}
