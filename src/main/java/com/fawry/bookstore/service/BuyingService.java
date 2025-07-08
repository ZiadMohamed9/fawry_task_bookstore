package com.fawry.bookstore.service;

import com.fawry.bookstore.entity.Book;
import com.fawry.bookstore.entity.BookType;
import com.fawry.bookstore.entity.User;
import com.fawry.bookstore.exception.UnsupportedBookType;
import com.fawry.bookstore.repository.BookRepository;
import com.fawry.bookstore.repository.UserRepository;
import com.fawry.bookstore.request.BuyBookRequest;
import com.fawry.bookstore.service.purchasestrategy.BookPurchaseStrategyFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BuyingService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookPurchaseStrategyFactory strategyFactory;

    @Transactional
    public double buyBook(@Valid BuyBookRequest request) {
        Book book = bookRepository.findById(request.getIsbn())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ISBN: " + request.getIsbn()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + request.getEmail()));

        BookType bookType = book.getBookType();
        if (bookType == BookType.SHOWCASE_BOOK)
            throw new UnsupportedBookType("Showcase books cannot be purchased.");

        BookPurchaseStrategy strategy = strategyFactory.getStrategy(bookType);
        return strategy.purchase(request);
    }
}
