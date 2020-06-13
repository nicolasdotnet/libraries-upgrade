/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.controllers;

import com.bigcity.entity.ExceptionMessage;
import com.bigcity.exceptions.EntityAlreadyExistsException;
import com.bigcity.exceptions.EntityNotFoundException;
import com.bigcity.exceptions.BookingNotPossibleException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<ExceptionMessage> userNoFindHandler(HttpServletRequest request, EntityNotFoundException exception) {
//        ExceptionMessage message = ExceptionMessage.builder()
//                .date(LocalDateTime.now().format(formatter))
//                .path(request.getRequestURI().toString() + "?" + request.getQueryString())
//                .className(exception.getClass().getName())
//                .message("Tu veux éviter les null ? N'hésite pas à lire cet article: https://www.developpez.net/forums/blogs/473169-gugelhupf/b2944/java-astuces-eviter-nullpointerexception/")
//                .build();
//        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity handleEntityNoFoudException(EntityNotFoundException error) {
//        // log exception
//        return ResponseEntity
//                .status(HttpStatus.FORBIDDEN)
//                .body("Error Message");
//    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleEntityNoFoudException(HttpServletRequest request, EntityNotFoundException e) {
        // log exception 

        ExceptionMessage message = ExceptionMessage.builder()
                .date(LocalDateTime.now().format(formatter))
                .path(request.getRequestURI() + "?" + request.getQueryString())
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ExceptionMessage> handleEntityAlreadyExistsException(HttpServletRequest request, EntityAlreadyExistsException e) {
        // log exception 

        ExceptionMessage message = ExceptionMessage.builder()
                .date(LocalDateTime.now().format(formatter))
                .path(request.getRequestURI() + "?" + request.getQueryString())
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookingNotPossibleException.class)
    public ResponseEntity<ExceptionMessage> handleBookingNotPossibleException(HttpServletRequest request, BookingNotPossibleException e) {
        // log exception

        ExceptionMessage message = ExceptionMessage.builder()
                .date(LocalDateTime.now().format(formatter))
                .path(request.getRequestURI() + "?" + request.getQueryString())
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();
//        return ResponseEntity.status(error.getHttpStatus()).body(error.getMessage());

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {

        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        List<String> getMessage = new ArrayList<>();

        for (FieldError error : errors) {
            getMessage.add("@" + error.getField().toUpperCase() + ":" + error.getDefaultMessage());
        }

        String text = String.format(getMessage.toString());

        ExceptionMessage message = ExceptionMessage.builder()
                .date(LocalDateTime.now().format(formatter))
                .path(request.getRequestURI() + "?" + request.getQueryString())
                .className(e.getClass().getName())
                .message(text)
                .build();

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}
