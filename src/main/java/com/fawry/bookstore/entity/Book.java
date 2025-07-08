package com.fawry.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(indexes = @Index(name = "idx_book_price", columnList = "price"))
public class Book {
    @Id
    private String isbn;
    private String title;

    @Column(name = "book_type")
    @Enumerated(EnumType.STRING)
    private BookType bookType;

    @Column(name = "publication_date")
    private LocalDate publicationDate;
    private double price;
    private int stock;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    public Book(String isbn, String title, BookType bookType, LocalDate publicationDate, double price, int stock) {
        this.isbn = isbn;
        this.title = title;
        this.bookType = bookType;
        this.publicationDate = publicationDate;
        this.price = price;
        this.stock = stock;
        this.authors = new HashSet<>();
    }
}
