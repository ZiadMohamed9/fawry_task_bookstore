package com.fawry.bookstore.repository;

import com.fawry.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findAllByAuthorsId(Long authorId);

    List<Book> findBooksByPriceBetween(double priceAfter, double priceBefore);

    List<Book> findByPublicationDateBefore(LocalDate cutOffDate);

    void deleteByPublicationDateBefore(LocalDate cutOffDate);

    boolean existsByIsbn(String isbn);
}