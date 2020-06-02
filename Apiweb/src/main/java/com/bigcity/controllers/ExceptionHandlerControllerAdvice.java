/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.controllers;

import com.bigcity.exceptions.ResourceException;
import com.bigcity.exceptions.UserNoFoundException;
import com.bigcity.exceptions.UsersNoFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author nicolasdotnet
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

//    @ExceptionHandler(UserNoFoundException.class)
//    public ResponseEntity<ExceptionMessage> userNoFindHandler(HttpServletRequest request, UserNoFoundException exception) {
//        ExceptionMessage message = ExceptionMessage.builder()
//                .date(LocalDateTime.now().format(formatter))
//                .path(request.getRequestURI().toString() + "?" + request.getQueryString())
//                .className(exception.getClass().getName())
//                .message("Tu veux éviter les null ? N'hésite pas à lire cet article: https://www.developpez.net/forums/blogs/473169-gugelhupf/b2944/java-astuces-eviter-nullpointerexception/")
//                .build();
//        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
    @ExceptionHandler(UserNoFoundException.class)
    public ResponseEntity handleException(UserNoFoundException e) {
        // log exception
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Error Message");
    }

    @ExceptionHandler(UsersNoFoundException.class)
    public ResponseEntity handleException(UsersNoFoundException e) {
        // log exception 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity handleException(ResourceException e) {
        // log exception 
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBindingErrors(MethodArgumentNotValidException ex) {

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<String> message = new ArrayList<>();

        for (FieldError e : errors) {
            message.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
        }

        String error = "Update Failed";

        String text = String.format(message.toString());

        message.toString();

        return ResponseEntity.badRequest().body(text);
    }

}
