package com.durandsuppicich.danmspedidos.service;

import com.durandsuppicich.danmspedidos.domain.Product;

import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    
    @Override
    public Integer getAvailableStock(Product product) {
        return Integer.MAX_VALUE;
    }
}
