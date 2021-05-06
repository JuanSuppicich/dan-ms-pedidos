package com.durandsuppicich.danmspedidos.exception;

public class UnauthorizedException extends RuntimeException {

    private static final String DESCRIPCION = "Unauthorized Exception (401)";

    public UnauthorizedException(String detalle) {
        super(DESCRIPCION + ". " + detalle);
    }

}
