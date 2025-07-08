package com.fawry.bookstore.service.purchasestrategy.strategyimpl;

import com.fawry.bookstore.exception.BookNotFoundException;
import com.fawry.bookstore.exception.UserNotFoundException;
import com.fawry.bookstore.repository.BookRepository;
import com.fawry.bookstore.repository.UserRepository;
import com.fawry.bookstore.request.BuyBookRequest;
import com.fawry.bookstore.service.BookPurchaseStrategy;
import com.fawry.bookstore.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("E_BOOK")
@RequiredArgsConstructor
public class EBookPurchaseStrategy implements BookPurchaseStrategy {
    private final EmailService emailService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public double purchase(BuyBookRequest request) {
        var book = bookRepository.findById(request.getIsbn())
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + request.getIsbn()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        double totalPrice = book.getPrice();

        // No stock check needed for e-books
        emailService.sendEmail(book, user);

        return totalPrice;
    }
}
