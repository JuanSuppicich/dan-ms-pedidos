package com.durandsuppicich.danmspedidos.exception;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class MensajeError {

    private String excepcion;

    private String mensaje;

    private String path;

    private Instant timeStamp;

    public MensajeError() {
    }

    public MensajeError(Exception excepcion, String path) {
        this.excepcion = excepcion.getClass().getSimpleName();
        this.mensaje = excepcion.getMessage();
        this.path = path;
        this.timeStamp = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public String getExcepcion() {
        return excepcion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getPath() {
        return path;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "MensajeError {" +
        "excepcion=" + excepcion + '\n' +
        ", mensaje=" + mensaje + '\n' +
        ", path=" + path + '\n' +
        ", timestamp=" + timeStamp + '\n' +
        "}";
    }

    

}
