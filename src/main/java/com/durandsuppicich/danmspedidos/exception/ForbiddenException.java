package com.durandsuppicich.danmspedidos.exception;

public class ForbiddenException extends RuntimeException {
    
    private static final String DESCRIPCION = "Forbidden Exception (403)";

    public ForbiddenException(String detalle) {
        super(DESCRIPCION + ". " + detalle);
    }
}

