package com.fawry.bookstore.service;

import com.fawry.bookstore.entity.Book;
import com.fawry.bookstore.exception.BookNotFoundException;
import com.fawry.bookstore.repository.BookRepository;
import com.fawry.bookstore.request.AddBookRequest;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public Book getByAuthor(String authorId) {
        return bookRepository.findByAuthor(authorId)
                .orElseThrow(() -> new BookNotFoundException("Book not found for author ID: " + authorId));
    }

    public List<Book> findBooksByPriceBetween(double priceAfter, double priceBefore) {
        return bookRepository.findBooksByPriceBetween(priceAfter, priceBefore);
    }

    @Transactional
    public void addBook(@Valid AddBookRequest request){
        Book book = new Book(
                request.getIsbn(),
                request.getTitle(),
                request.getBookType(),
                request.getPublicationDate(),
                request.getPrice(),
                request.getStock()
        );
        bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(String isbn){
        bookRepository.deleteById(isbn);
    }

    @Transactional
    public List<Book> deleteOutDatedBooks(int years) {
        List<Book> outdatedBooks = bookRepository.findOutdatedBooks(years);
        outdatedBooks.forEach(book -> bookRepository.deleteById(book.getIsbn()));
        return outdatedBooks;
    }

    public void updateBook(String isbn, @Valid AddBookRequest request) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(request.getTitle());
        book.setBookType(request.getBookType());
        book.setPublicationDate(request.getPublicationDate());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());

        bookRepository.save(book);
    }
}
