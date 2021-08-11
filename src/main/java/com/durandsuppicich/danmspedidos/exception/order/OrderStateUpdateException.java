package com.durandsuppicich.danmspedidos.exception.order;

import com.durandsuppicich.danmspedidos.exception.http.BadRequestException;

public class OrderStateUpdateException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "Illegal order state update. Order cannot be updated to the given state. ";

    public OrderStateUpdateException() {
        super(DEFAULT_MESSAGE);
    }

    public OrderStateUpdateException(String currentState, String state) {
        super(DEFAULT_MESSAGE + "Current state is " + "{" + currentState + "}"
            + "  while the given state is " + "{" + state + "}. " + "Review order's state machine.");
}
}
