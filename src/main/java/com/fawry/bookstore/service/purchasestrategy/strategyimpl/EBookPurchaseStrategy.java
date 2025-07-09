package com.fawry.bookstore.service.purchasestrategy.strategyimpl;

import com.fawry.bookstore.entity.Book;
import com.fawry.bookstore.entity.User;
import com.fawry.bookstore.exception.BookNotFoundException;
import com.fawry.bookstore.exception.UserNotFoundException;
import com.fawry.bookstore.repository.BookRepository;
import com.fawry.bookstore.repository.UserRepository;
import com.fawry.bookstore.request.BuyBookRequest;
import com.fawry.bookstore.service.purchasestrategy.BookPurchaseStrategy;
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
        Book book = request.getBook();
        User user = request.getUser();

        double totalPrice = book.getPrice();

        // No stock check needed for e-books
        emailService.sendEmail(book, user);

        return totalPrice;
    }
}
