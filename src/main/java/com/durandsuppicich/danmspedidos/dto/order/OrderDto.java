package com.durandsuppicich.danmspedidos.dto.order;

import java.time.Instant;

public class OrderDto {

    private Integer id;
    private Instant shippingDate;
    private String stateDescription;
    private Integer stateId;
    private String constructionDescription;
    private Integer itemCount;
    private Double totalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Instant shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getConstructionDescription() {
        return constructionDescription;
    }

    public void setConstructionDescription(String constructionDescription) {
        this.constructionDescription = constructionDescription;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
