package com.durandsuppicich.danmspedidos.exception.item;

public class OrderItemIdNotFoundException extends OrderItemNotFoundException {

    private static final String DEFAULT_MESSAGE = "The given id does not belong to any order item.";

    public OrderItemIdNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public OrderItemIdNotFoundException(Integer id) {
        super(DEFAULT_MESSAGE + " {" + id + "}");
    }
}
