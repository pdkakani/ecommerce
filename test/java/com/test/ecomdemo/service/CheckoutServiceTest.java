package com.test.ecomdemo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CheckoutServiceTest {

    @Autowired
    private CheckoutService checkoutService;

    @Test
    void getCartPrice() {
        var receipt = checkoutService.getCartPrice(List.of(001, 002, 001, 001, 003));
        assertEquals(330, receipt.getPrice());
    }

}