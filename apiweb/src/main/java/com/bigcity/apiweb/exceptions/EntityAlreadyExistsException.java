/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.exceptions;

/**
 *
 * @author nicolasdotnet
 */

public class EntityAlreadyExistsException extends LibraryException {

    public EntityAlreadyExistsException() {
    }

    public EntityAlreadyExistsException(String message) {

        super(message);
    }

}
