package com.durandsuppicich.danmspedidos.exception;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ErrorMessage {

    private String exception;

    private String message;

    private String path;

    private Instant timeStamp;

    public ErrorMessage() { }

    public ErrorMessage(Exception exception, String path) {
        this.exception = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.path = path;
        this.timeStamp = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public ErrorMessage(Exception exception, String path, String message) {
        this.exception = exception.getClass().getSimpleName();
        this.message = message;
        this.path = path;
        this.timeStamp = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "ErrorMessage {" +
                "exception=" + exception + '\n' +
                ", message=" + message + '\n' +
                ", path=" + path + '\n' +
                ", timestamp=" + timeStamp + '\n' +
                "}";
    }
}
