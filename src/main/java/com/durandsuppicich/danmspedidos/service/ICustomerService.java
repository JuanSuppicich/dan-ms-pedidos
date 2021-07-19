package com.durandsuppicich.danmspedidos.service;

import com.durandsuppicich.danmspedidos.domain.Construction;

public interface ICustomerService {

    Double getBalance(Construction construction);

    Double getMaximumNegativeBalance(Construction construction);
}
