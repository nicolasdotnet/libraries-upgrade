/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author nicolasdotnet
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsersNoFindException extends LibraryException {

    public UsersNoFindException(String message) {
        
        super(message);
    }
    
}
