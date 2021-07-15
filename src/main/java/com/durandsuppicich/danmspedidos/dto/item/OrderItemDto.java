package com.durandsuppicich.danmspedidos.dto.item;

public class OrderItemDto {

    private Integer id;
    private Integer quantity;
    private Double price;
    private String productDescription;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
