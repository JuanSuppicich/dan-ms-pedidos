package com.durandsuppicich.danmspedidos.exception;

public class NotFoundException extends RuntimeException {
    
    private static final String DESCRIPCION = "Not Found Exception (404)";

    public NotFoundException(String detalle) {
        super(DESCRIPCION + ". " + detalle);
    }

}
