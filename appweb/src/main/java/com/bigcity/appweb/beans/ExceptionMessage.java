package com.bigcity.appweb.beans;

import java.util.Map;

/**
 * Exception Message 
 *
 * @author nicolasdotnet
 */
public class ExceptionMessage {
    
    
    private Map<Integer, Message[]> exception;

    public ExceptionMessage() {
    }

    public ExceptionMessage(Map<Integer, Message[]> exception) {
        this.exception = exception;
    }

    public Map<Integer, Message[]> getException() {
        return exception;
    }

    public void setException(Map<Integer, Message[]> exception) {
        this.exception = exception;
    }

    
    
    
}
