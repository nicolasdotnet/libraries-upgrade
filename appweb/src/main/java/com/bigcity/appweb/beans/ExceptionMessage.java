package com.bigcity.appweb.beans;

import com.bigcity.appweb.tools.ExceptionMessageBuilder;

/**
 * Exception Message
 *
 * @author nicolasdotnet
 */
public class ExceptionMessage {

    private String message;

    // Builder Initialitation
    public static ExceptionMessageBuilder builder() {
        return new ExceptionMessageBuilder();
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
