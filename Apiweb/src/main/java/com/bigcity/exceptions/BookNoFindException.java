/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.exceptions;

/**
 *
 * @author nicolasdotnet
 */
public class BookNoFindException extends LibraryException {

    public BookNoFindException(String message) {

        super(message);
    }
    
}
