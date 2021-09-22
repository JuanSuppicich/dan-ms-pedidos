package com.durandsuppicich.danmspedidos.dto.item;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrderItemPostDto {

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Positive
    private Integer productId;

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getProductId() {
        return productId;
    }
}
