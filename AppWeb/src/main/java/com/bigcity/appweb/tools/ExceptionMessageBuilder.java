/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.tools;

import com.bigcity.appweb.beans.ExceptionMessage;



/**
 *
 * @author nicolasdotnet
 */
public class ExceptionMessageBuilder {

    private String message;


    public ExceptionMessageBuilder() {
    }

    public ExceptionMessageBuilder(ExceptionMessage exceptionMessage) {
        this.message = exceptionMessage.getMessage();
    }

    public ExceptionMessageBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ExceptionMessage build() {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setMessage(message);
        return exceptionMessage;
    }

}
