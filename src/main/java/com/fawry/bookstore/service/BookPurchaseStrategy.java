package com.fawry.bookstore.service;

import com.fawry.bookstore.request.BuyBookRequest;

public interface BookPurchaseStrategy {
    double purchase(BuyBookRequest request);
}
