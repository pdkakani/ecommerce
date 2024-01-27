package com.test.ecomdemo.service;

import com.test.ecomdemo.exception.ProductNotFoundException;
import com.test.ecomdemo.model.Receipt;
import com.test.ecomdemo.model.Watch;
import com.test.ecomdemo.repository.WatchRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class CheckoutService {

    private final WatchRepository watchRepository;

    public CheckoutService(WatchRepository watchRepository) {
        this.watchRepository = watchRepository;
    }

    /**
     * Gets Watch details from the database by id
     * @param id: Watch id
     * @return watch object
     */
    @SneakyThrows
    public Watch getWatchById(int id) {
        var watch = watchRepository.findById(id);
        if (watch.isPresent()) {
            return watch.get();
        } else {
            log.debug("Unable to retrieve watch from the database");
            throw new ProductNotFoundException("Watch number " + id + " is not available at the moment");
        }
    }

    /**
     * Gets the price of all items in the cart using a list of watch Ids
     * @param watchIds: List of watch ids
     * @return receipt (checkout price)
     */
    public Receipt getCartPrice(List<Integer> watchIds) {
        var cart = getCart(watchIds);
        var priceList = new ArrayList<>();
        cart.forEach((key, value) -> {
            var watch = getWatchById(key);
            priceList.add(getTotalPriceForWatch(watch, value));
        });
        var price = priceList.stream().mapToDouble(p -> (double) p).sum();
        var receipt = new Receipt();
        receipt.setPrice(price);
        return receipt;
    }

    /**
     * Gets a map of watchIds and their respective quantities
     * @param watchIds: List of watch ids
     * @return map of cart items and their quantities
     */
    public static HashMap<Integer, Integer> getCart(List<Integer> watchIds) {
        var cart = new HashMap<Integer, Integer>();
        for (var id: watchIds) {
            if (cart.containsKey(id)) {
                cart.put(id, cart.get(id) + 1);
            } else {
                cart.put(id, 1);
            }
        }
        return cart;
    }

    /**
     *
     * @param price: original price of the watch
     * @param discountOnPackOf: quantity of watches on which the discount is applicable
     * @param discountPrice: discounted price of watch
     * @param packs: number of pack of watches
     * @return discount price of pack of watches
     */
    private static double getDiscountPriceForPacks(double price, int discountOnPackOf, double discountPrice, int packs) {
        var discountFactor = discountPrice / (discountOnPackOf * price);
        return discountOnPackOf * packs * price * discountFactor;
    }


    /**
     *
     * @param watch: watch to get the price for
     * @param quantity: quantity of watches to buy
     * @return total price of all watches of the same model
     */
    private static double getTotalPriceForWatch(Watch watch, int quantity) {
        double price;
        var packsOfWatch= 0;
        if (watch.getDiscountOnPackOf() > 0) {
            packsOfWatch = quantity / watch.getDiscountOnPackOf();
        }
        if (packsOfWatch > 0) {
            price = getDiscountPriceForPacks(
                    watch.getPrice(),
                    watch.getDiscountOnPackOf(),
                    watch.getDiscountPrice(),
                    packsOfWatch) + watch.getPrice() * (quantity % watch.getDiscountOnPackOf());
        } else {
            price = quantity * watch.getPrice();
        }
        return price;
    }
}
