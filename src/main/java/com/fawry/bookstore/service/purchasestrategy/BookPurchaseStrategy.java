package com.fawry.bookstore.service.purchasestrategy;

import com.fawry.bookstore.request.BuyBookRequest;

public interface BookPurchaseStrategy {
    double purchase(BuyBookRequest request);
}
