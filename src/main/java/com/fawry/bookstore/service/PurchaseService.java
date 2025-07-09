package com.fawry.bookstore.service;

import com.fawry.bookstore.entity.Book;
import com.fawry.bookstore.entity.BookType;
import com.fawry.bookstore.entity.Purchase;
import com.fawry.bookstore.entity.User;
import com.fawry.bookstore.exception.BookNotFoundException;
import com.fawry.bookstore.exception.UnsupportedBookType;
import com.fawry.bookstore.exception.UserNotFoundException;
import com.fawry.bookstore.repository.BookRepository;
import com.fawry.bookstore.repository.PurchaseRepository;
import com.fawry.bookstore.repository.UserRepository;
import com.fawry.bookstore.request.BuyBookRequest;
import com.fawry.bookstore.service.purchasestrategy.BookPurchaseStrategy;
import com.fawry.bookstore.service.purchasestrategy.BookPurchaseStrategyFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PurchaseService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;
    private final BookPurchaseStrategyFactory strategyFactory;

    public double buyBook(@Valid BuyBookRequest request) {
        Book book = request.getBook();
        User user = request.getUser();
        if (book == null || user == null)
            throw new IllegalArgumentException("Book and User must not be null");

        if (!bookRepository.existsByIsbn(book.getIsbn()))
            throw new BookNotFoundException("Book not found with ISBN: " + book.getIsbn());

        if (!userRepository.existsByEmail(user.getEmail()))
            throw new UserNotFoundException("User not found with email: " + user.getEmail());

        BookType bookType = book.getBookType();
        if (bookType == BookType.SHOWCASE_BOOK)
            throw new UnsupportedBookType("Showcase books cannot be purchased.");

        BookPurchaseStrategy strategy = strategyFactory.getStrategy(bookType);
        double paidAmount = strategy.purchase(request);

        Purchase purchase = new Purchase(user, book, request.getQuantity(), paidAmount);
        purchaseRepository.save(purchase);

        return paidAmount;
    }
}
