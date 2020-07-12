package com.bigcity.apiweb.entity;

import com.bigcity.apiweb.tools.ExceptionMessageBuilder;

/**
 *
 * @author nicolasdotnet
 * 
 * ExceptionMessage is the custmization class for error messages
 * 
 */
public class ExceptionMessage {

    private String message;
    private String className;
    private String path;
    private String date;

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

    @Override
    public String toString() {
        return "ExceptionMessage{" + "message=" + message + ", className=" + className + ", path=" + path + ", date=" + date + '}';
    }

}
