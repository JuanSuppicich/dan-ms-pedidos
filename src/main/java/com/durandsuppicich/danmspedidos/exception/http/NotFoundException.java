package com.durandsuppicich.danmspedidos.exception.http;

public class NotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Not Found Exception (404).";

    public NotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }
}
