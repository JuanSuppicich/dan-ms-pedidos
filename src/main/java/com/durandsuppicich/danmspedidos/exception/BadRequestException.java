package com.durandsuppicich.danmspedidos.exception;

//Este tipo de clase generica se pondria extender para especificar. 
public class BadRequestException extends RuntimeException {
    
    private static final String DESCRIPCION = "Bad Request Exception (400)";

    public BadRequestException(String detalle) {
        super(DESCRIPCION + ". " + detalle);
    }
}
