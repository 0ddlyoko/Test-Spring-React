package me.oddlyoko.demo.payloads.responses;

import java.time.Instant;

public record ExceptionResponse(String error, int status, Instant timestamp) {

    public ExceptionResponse(String error, int status) {
        this(error, status, Instant.now());
    }
}
