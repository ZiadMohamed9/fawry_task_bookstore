package com.fawry.bookstore.service.purchasestrategy.strategyimpl;

import com.fawry.bookstore.exception.BookNotFoundException;
import com.fawry.bookstore.exception.OutOfStockException;
import com.fawry.bookstore.exception.UserNotFoundException;
import com.fawry.bookstore.repository.BookRepository;
import com.fawry.bookstore.repository.UserRepository;
import com.fawry.bookstore.request.BuyBookRequest;
import com.fawry.bookstore.service.BookPurchaseStrategy;
import com.fawry.bookstore.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("PAPER_BOOK")
@RequiredArgsConstructor
public class PaperBookPurchaseStrategy implements BookPurchaseStrategy {
    private final ShippingService shippingService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public double purchase(BuyBookRequest request) {
        var book = bookRepository.findById(request.getIsbn())
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + request.getIsbn()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

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
