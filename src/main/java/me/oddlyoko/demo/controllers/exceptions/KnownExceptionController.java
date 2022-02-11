package me.oddlyoko.demo.controllers.exceptions;

import me.oddlyoko.demo.payloads.responses.ExceptionResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class KnownExceptionController {

    private ResponseEntity<ExceptionResponse> makeResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(new ExceptionResponse(message, status.value()), status);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(NoHandlerFoundException ex) {
        return makeResponse(String.format("Route %s %s not found", ex.getHttpMethod(), ex.getRequestURL()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(MethodArgumentTypeMismatchException ex) {
        return makeResponse(String.format("Given argument (%s) is not suitable argument for %s", ex.getValue(), ex.getName()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return makeResponse(String.format("Request method '%s' not supported for %s", ex.getMethod(), request.getDescription(false)), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            HttpMessageConversionException.class,
            javax.validation.ConstraintViolationException.class,
    })
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveDefaultBadRequestException(Exception ex) {
        return makeResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(ConstraintViolationException ex) {
        return makeResponse(ex.getSQLException().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof ConstraintViolationException constraintViolationException)
            return resolveException(constraintViolationException);
        return resolveException((Exception) ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(Exception ex) {
        // TODO Use logger
        ex.printStackTrace();
        return makeResponse("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
