package com.fawry.bookstore.service.purchasestrategy.strategyimpl;

import com.fawry.bookstore.repository.BookRepository;
import com.fawry.bookstore.repository.UserRepository;
import com.fawry.bookstore.request.BuyBookRequest;
import com.fawry.bookstore.service.BookPurchaseStrategy;
import com.fawry.bookstore.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EBookPurchaseStrategy implements BookPurchaseStrategy {
    private final EmailService emailService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public double purchase(BuyBookRequest request) {
        var book = bookRepository.findById(request.getIsbn())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ISBN: " + request.getIsbn()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + request.getEmail()));

        int quantity = request.getQuantity();
        double totalPrice = book.getPrice() * quantity;

        // No stock check needed for e-books
        emailService.sendEmail(book, user);

        return totalPrice;
    }
}
