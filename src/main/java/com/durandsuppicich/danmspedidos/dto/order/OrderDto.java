package com.durandsuppicich.danmspedidos.dto.order;

import java.time.Instant;

public class OrderDto {

    private Integer id;
    private Instant shippingDate;
    private String stateDescription;
    private String constructionDescription;
    private Integer itemCount;
    private Double totalPrice;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setShippingDate(Instant shippingDate) {
        this.shippingDate = shippingDate;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }

    public void setConstructionDescription(String constructionDescription) {
        this.constructionDescription = constructionDescription;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
