package com.durandsuppicich.danmspedidos.dto.order;

import com.durandsuppicich.danmspedidos.dto.item.OrderItemPostDto;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.List;

public class OrderPostDto {

    @NotNull
    @Future
    private Instant shippingDate;

    @NotNull
    @Positive
    private Integer constructionId;

    @NotEmpty
    private List<@Valid OrderItemPostDto> items;

    public Instant getShippingDate() {
        return shippingDate;
    }

    public Integer getConstructionId() {
        return constructionId;
    }

    public List<OrderItemPostDto> getItems() {
        return items;
    }
}
