package com.durandsuppicich.danmspedidos.dto.order;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrderPatchDto {

    @NotNull
    @Positive
    private Integer id;

    @NotNull
    @Positive
    private Integer orderStateId;

    public Integer getId() {
        return id;
    }

    public Integer getOrderStateId() {
        return orderStateId;
    }
}
