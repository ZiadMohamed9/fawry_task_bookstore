package com.fawry.bookstore.service.purchasestrategy;

import com.fawry.bookstore.entity.BookType;
import com.fawry.bookstore.exception.UnsupportedBookType;
import com.fawry.bookstore.service.BookPurchaseStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class BookPurchaseStrategyFactory {
    private final Map<String, BookPurchaseStrategy> strategies;

    public BookPurchaseStrategy getStrategy(BookType bookType) {
        BookPurchaseStrategy strategy = strategies.get(bookType.toString());
        if (strategy == null) {
            throw new UnsupportedBookType("Unknown book type: " + bookType);
        }
        return strategy;
    }
}