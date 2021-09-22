package com.durandsuppicich.danmspedidos.dto.item;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrderItemPutDto {

    @NotNull
    @Positive
    private Integer id;

    @NotNull
    @Positive
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
