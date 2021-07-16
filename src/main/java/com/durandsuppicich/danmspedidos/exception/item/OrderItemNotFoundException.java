package com.durandsuppicich.danmspedidos.exception.item;

import com.durandsuppicich.danmspedidos.exception.http.NotFoundException;

public class OrderItemNotFoundException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "Order item could not be found.";

    public OrderItemNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public OrderItemNotFoundException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }
}
