package com.durandsuppicich.danmspedidos.service;

import com.durandsuppicich.danmspedidos.domain.Product;

public interface IProductService {

    Integer getAvailableStock(Product product);
}
