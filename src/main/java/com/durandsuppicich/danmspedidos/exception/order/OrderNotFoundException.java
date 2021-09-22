package com.durandsuppicich.danmspedidos.exception.order;

import com.durandsuppicich.danmspedidos.exception.http.NotFoundException;

public class OrderNotFoundException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "Order could not be found.";

    public OrderNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public OrderNotFoundException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }
}
