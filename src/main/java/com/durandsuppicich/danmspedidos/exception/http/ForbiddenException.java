package com.durandsuppicich.danmspedidos.exception.http;

public class ForbiddenException extends RuntimeException {
    
    private static final String DEFAULT_MESSAGE = "Forbidden Exception (403).";

    public ForbiddenException() {
        super(DEFAULT_MESSAGE);
    }

    public ForbiddenException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }
}
