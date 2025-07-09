package com.fawry.bookstore.service.purchasestrategy.strategyimpl;

import com.fawry.bookstore.entity.Book;
import com.fawry.bookstore.entity.User;
import com.fawry.bookstore.exception.InsufficientQuantityException;
import com.fawry.bookstore.exception.OutOfStockException;
import com.fawry.bookstore.repository.BookRepository;
import com.fawry.bookstore.repository.UserRepository;
import com.fawry.bookstore.request.BuyBookRequest;
import com.fawry.bookstore.service.purchasestrategy.BookPurchaseStrategy;
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
        Book book = request.getBook();
        User user = request.getUser();

        int quantity = request.getQuantity();
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        int stock = book.getStock();
        if (stock == 0) {
            throw new OutOfStockException("Book: " + book.getTitle() + " is out of stock.");
        }
        if (stock < quantity) {
            throw new InsufficientQuantityException(
                "Insufficient stock for book: " + book.getTitle() + ". Requested: " + quantity + ", Available: " + stock);
        }

        book.setStock(stock - quantity);
        bookRepository.save(book);

        double totalPrice = book.getPrice() * quantity;
        shippingService.shipBook(book, user);

        return totalPrice;
    }
}
