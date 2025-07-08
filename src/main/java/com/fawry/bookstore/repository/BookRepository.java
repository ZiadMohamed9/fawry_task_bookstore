package com.fawry.bookstore.repository;

import com.fawry.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    @Query(
            "SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId"
    )
    Optional<Book> findByAuthor(String authorId);

    List<Book> findBooksByPriceBetween(double priceAfter, double priceBefore);

    @Query(
            "SELECT b FROM Book b WHERE b.publicationDate < CURRENT_DATE - INTERVAL (:years) YEAR"
    )
    List<Book> findOutdatedBooks(int years);

    @Modifying
    @Query(
            "DELETE FROM Book b WHERE b.publicationDate < CURRENT_DATE - INTERVAL (:years) YEAR"
    )
    void deleteOutdatedBooks(int years);
}