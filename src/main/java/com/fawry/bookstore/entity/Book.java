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
@Table(indexes = {
        @Index(name = "idx_book_isbn", columnList = "isbn"),
        @Index(name = "idx_book_price", columnList = "price")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String isbn;
    private String title;
    private BookType bookType;
    private LocalDate publicationDate;
    private double price;
    private int stock;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
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
