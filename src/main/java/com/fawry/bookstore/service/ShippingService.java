package com.fawry.bookstore.service;

import com.fawry.bookstore.entity.Book;
import com.fawry.bookstore.entity.User;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {
    public void shipBook(Book book, User user) {
        String shippingAddress = user.getAddress();
        String bookTitle = book.getTitle();
        System.out.println("Shipping book '" + bookTitle + "' to " + user.getName() + " at address: " + shippingAddress);
    }
}
