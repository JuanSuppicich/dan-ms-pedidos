package com.durandsuppicich.danmspedidos.exception.order;

import com.durandsuppicich.danmspedidos.exception.http.BadRequestException;

public class OrderStateUpdateException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "Illegal order state update. Order can not be updated to given state. ";

    public OrderStateUpdateException() {
        super(DEFAULT_MESSAGE);
    }

    public OrderStateUpdateException(String currentState, String state) {
        super(DEFAULT_MESSAGE + "Current state is " + "{" + currentState + "}"
            + "  while the given state is " + "{" + state + "}. " + "Review order's state machine.");
}
}
