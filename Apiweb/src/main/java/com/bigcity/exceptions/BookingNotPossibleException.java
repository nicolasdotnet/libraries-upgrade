/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * @author nicolasdotnet
 */
public class BookingNotPossibleException extends LibraryException {

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public BookingNotPossibleException() {
    }

    public BookingNotPossibleException(String string) {
        super(string);
    }

    public BookingNotPossibleException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
