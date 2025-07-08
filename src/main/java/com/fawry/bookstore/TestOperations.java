package com.fawry.bookstore;

import com.fawry.bookstore.entity.Author;
import com.fawry.bookstore.entity.Book;
import com.fawry.bookstore.entity.BookType;
import com.fawry.bookstore.request.AddBookRequest;
import com.fawry.bookstore.request.AddUserRequest;
import com.fawry.bookstore.request.BuyBookRequest;
import com.fawry.bookstore.service.AuthorService;
import com.fawry.bookstore.service.BookService;
import com.fawry.bookstore.service.BuyingService;
import com.fawry.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TestOperations {
    private final UserService userService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final BuyingService buyingService;

    @Bean
    public CommandLineRunner testRunner() {
        return args -> {
            try {
                testAddingBook();

                testDeletingBook();

                testDeletingOutdatedBooks();

                testAddingAuthor();

                testBuyingBook();
            }
            catch (Exception e) {
                System.err.println("Error during test operations: " + e.getMessage());
            }
        };
    }

    void testAddingBook() {
        Book book = new Book("1234567890", "Test Book", BookType.E_BOOK, LocalDate.of(2023, 1, 1), 9.99, 100);
        bookService.addBook(book);
        System.out.println("Book added successfully: " + bookService.getBookById("1234567890").getTitle() );
    }

    void testDeletingBook() {
        String isbn = "1234567890";
        bookService.deleteBook(isbn);
        System.out.println("Book deleted successfully: " + isbn);
    }

    void testDeletingOutdatedBooks() {
        // first, add some books to test deletion
        bookService.addBook(new Book("1234567800", "Old Book", BookType.E_BOOK, LocalDate.of(2015, 1, 1), 5.99, 50));
        bookService.addBook(new Book("1234567801", "Old Book 2", BookType.E_BOOK, LocalDate.of(2013, 1, 1), 10.99, 20));
        bookService.addBook(new Book("1234567801", "Recent Book", BookType.E_BOOK, LocalDate.of(2023, 1, 1), 15.99, 30));
        System.out.println("Books added for deletion test.");

        int years = 5;
        List<Book> deletedBooks = bookService.deleteOutDatedBooks(years);
        deletedBooks.forEach(book -> System.out.println("Book deleted successfully: " + book.getTitle()));
    }

    void testAddingAuthor() {
        authorService.addAuthor(new Author("John Doe", LocalDate.of(1980, 1, 1)));
        System.out.println("Author added successfully: " + authorService.getAuthorByName("John Doe").getName());
    }

    void testBuyingBook() {
        var request = AddUserRequest.builder()
                .username("testUser")
                .email("user@email.com")
                .password("<PASSWORD>")
                .role("USER")
                .address("Test Address")
                .build();
        userService.addUser(request);
        System.out.println("User added successfully: " + userService.getUserByUsername("testUser").getUsername());

        var bookRequest = AddBookRequest.builder()
                .isbn("1234567800")
                .title("Test Book")
                .bookType(BookType.E_BOOK)
                .publicationDate(LocalDate.of(2023, 1, 1))
                .price(9.99)
                .stock(100)
                .build();
        bookService.addBook(bookRequest);
        System.out.println("Book added successfully: " + bookService.getBookById("1234567800").getTitle());

        var buyingRequest = BuyBookRequest.builder()
                .isbn("1234567800")
                .quantity(1)
                .email("user@email.com")
                .address("Test Address")
                .build();
        double paidAmount = buyingService.buyBook(buyingRequest);
        System.out.println("Book purchased successfully. Paid amount: " + paidAmount);
    }
}
