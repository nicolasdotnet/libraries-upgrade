package com.bigcity.appweb.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Exception Message 
 *
 * @author nicolasdotnet
 */
public class ExceptionMessage {
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("className")
    private String className;
    
    @JsonProperty("path")
    private String path;
    
    @JsonProperty("date")
    private String date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
