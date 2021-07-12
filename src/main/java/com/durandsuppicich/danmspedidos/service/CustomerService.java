package com.durandsuppicich.danmspedidos.service;

import com.durandsuppicich.danmspedidos.domain.Construction;

import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {

    @Override
    public Double getBalance(Construction construction) {
        return 0.0;
    }

    @Override
    public Double getMaximumNegativeBalance(Construction construction) {
        return 10000.0;
    }
}
