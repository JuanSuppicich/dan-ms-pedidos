package com.durandsuppicich.danmspedidos.exception.http;

public class ConflictException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Conflict Exception (409).";

    public ConflictException() {
        super(DEFAULT_MESSAGE);
    }

    public ConflictException(String message) {
        super(DEFAULT_MESSAGE + ". " + message);
    }
}
