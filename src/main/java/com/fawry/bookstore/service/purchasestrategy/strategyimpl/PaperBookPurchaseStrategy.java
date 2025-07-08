package com.fawry.bookstore.service.purchasestrategy.strategyimpl;

import com.fawry.bookstore.exception.OutOfStockException;
import com.fawry.bookstore.repository.BookRepository;
import com.fawry.bookstore.repository.UserRepository;
import com.fawry.bookstore.request.BuyBookRequest;
import com.fawry.bookstore.service.BookPurchaseStrategy;
import com.fawry.bookstore.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaperBookPurchaseStrategy implements BookPurchaseStrategy {
    private final ShippingService shippingService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public double purchase(BuyBookRequest request) {
        var book = bookRepository.findById(request.getIsbn())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ISBN: " + request.getIsbn()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + request.getEmail()));

        int quantity = request.getQuantity();
        if (book.getStock() < request.getQuantity()) {
            throw new OutOfStockException("Book: " + book.getTitle() + " is out of stock.");
        }

        book.setStock(book.getStock() - quantity);
        bookRepository.save(book);

        double totalPrice = book.getPrice() * quantity;
        shippingService.shipBook(book, user);

        return totalPrice;
    }
}
