package com.durandsuppicich.danmspedidos.exception.order;

import com.durandsuppicich.danmspedidos.exception.http.NotFoundException;

public class OrderStateNotFoundException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "Order state could not be found.";

    public OrderStateNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public OrderStateNotFoundException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }
}
