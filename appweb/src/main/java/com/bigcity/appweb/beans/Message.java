/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.beans;

/**
 *
 * @author nicolasdotnet
 */
public class Message {

    private String message;
    private String className;
    private String path;
    private String date;

    public Message() {
    }

    public Message(String message, String className, String path, String date) {
        this.message = message;
        this.className = className;
        this.path = path;
        this.date = date;
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
