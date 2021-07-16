package com.durandsuppicich.danmspedidos.exception.order;

public class OrderIdNotFoundException extends OrderNotFoundException {

    private static final String DEFAULT_MESSAGE = "The given id does not belong to any order.";

    public OrderIdNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public OrderIdNotFoundException(Integer id) {
        super(DEFAULT_MESSAGE + " {" + id + "}");
    }
}
