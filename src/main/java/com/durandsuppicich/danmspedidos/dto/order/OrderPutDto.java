package com.durandsuppicich.danmspedidos.dto.order;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

public class OrderPutDto {

    @NotNull
    @Positive
    private Integer id;

    @NotNull
    @Future
    private Instant shippingDate;

    @NotNull
    @Positive
    private Integer constructionId;

    public Integer getId() {
        return id;
    }

    public Instant getShippingDate() {
        return shippingDate;
    }

    public Integer getConstructionId() {
        return constructionId;
    }
}
