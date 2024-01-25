package com.test.ecomdemo.controller;

import com.test.ecomdemo.model.Receipt;
import com.test.ecomdemo.service.CheckoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }
    @PostMapping("/checkout")
    public ResponseEntity<Receipt> checkout(@RequestBody List<Integer> watchIds) {
        log.info("Retrieving receipt...");
        return ResponseEntity.ok(checkoutService.getCartPrice(watchIds));
    }
}
