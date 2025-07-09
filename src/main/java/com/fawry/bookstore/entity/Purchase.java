package com.fawry.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_isbn", nullable = false)
    private Book book;
    private int quantity;
    private double paidAmount;
    private LocalDate purchaseDate;

    public Purchase(User user, Book book, int quantity, double paidAmount) {
        this.user = user;
        this.book = book;
        this.paidAmount = paidAmount;
        this.purchaseDate = LocalDate.now();
    }
}
