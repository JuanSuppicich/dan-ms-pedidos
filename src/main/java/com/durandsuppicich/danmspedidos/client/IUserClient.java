package com.durandsuppicich.danmspedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("users")
public interface IUserClient {

    @GetMapping(value = "/api/customers/balance", params = "constructionId")
    Double getCustomerBalance(@RequestParam(name = "constructionId") Integer constructionId);

    @GetMapping(value = "/api/customers/balance/max-negative", params = "constructionId")
    Double getCustomerMaximumNegativeBalance(@RequestParam(name = "constructionId") Integer constructionId);

}
