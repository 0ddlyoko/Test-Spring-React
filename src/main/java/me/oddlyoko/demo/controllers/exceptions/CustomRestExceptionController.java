package me.oddlyoko.demo.controllers.exceptions;

import me.oddlyoko.demo.exceptions.ResourceNotFoundException;
import me.oddlyoko.demo.payloads.responses.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomRestExceptionController {

    private ResponseEntity<ExceptionResponse> makeResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(new ExceptionResponse(message, status.value()), status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(ResourceNotFoundException ex) {
        return makeResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
