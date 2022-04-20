package com.example.mancalagame.controllers;

import com.example.mancalagame.exceptions.GameIsNotEndedException;
import com.example.mancalagame.exceptions.GameIsNotStartedException;
import com.example.mancalagame.exceptions.StonesOfBigPitCanNotBePlayedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);


    @ExceptionHandler(value = {GameIsNotStartedException.class})
    public ResponseEntity<String> gameIsNotStartedException(GameIsNotStartedException ex, WebRequest request)
    {
        LOG.debug("handling GameISNotStartedException...");
        return ResponseEntity.badRequest().body("Exception = " + ex.getClass().getSimpleName());
    }

    @ExceptionHandler(value = {StonesOfBigPitCanNotBePlayedException.class})
    public ResponseEntity<String> stonesOfBigPitCanNotBePlayedException(StonesOfBigPitCanNotBePlayedException ex, WebRequest request)
    {
        LOG.debug("handling StonesOfBigPitCanNotBePlayedException...");
        return ResponseEntity.badRequest().body("Exception = " + ex.getClass().getSimpleName());
    }

    @ExceptionHandler(value = {GameIsNotEndedException.class})
    public ResponseEntity<String> gameIsNotEndedException(GameIsNotEndedException ex, WebRequest request)
    {
        LOG.debug("handling GameIsNotEndedException...");
        return ResponseEntity.badRequest().body("Exception = " + ex.getClass().getSimpleName());
    }
}
