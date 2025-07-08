package com.fawry.bookstore;

import com.fawry.bookstore.entity.Author;
import com.fawry.bookstore.entity.Book;
import com.fawry.bookstore.entity.BookType;
import com.fawry.bookstore.entity.Role;
import com.fawry.bookstore.request.AddBookRequest;
import com.fawry.bookstore.request.AddUserRequest;
import com.fawry.bookstore.request.BuyBookRequest;
import com.fawry.bookstore.service.AuthorService;
import com.fawry.bookstore.service.BookService;
import com.fawry.bookstore.service.PurchaseService;
import com.fawry.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class TestOperations {
    private final UserService userService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final PurchaseService buyingService;

    @Bean
    public CommandLineRunner testRunner() {
        return _ -> {
            try {
                testAddingBook();

//                testDeletingBook();

//                testDeletingOutdatedBooks();

//                testAddingAuthor();

//                testAddingAuthorWithBooks();

                testAddUsers();

                testBuyingBook();
            }
            catch (Exception e) {
                System.err.println("Error during test operations: " + e.getMessage());
            }
        };
    }

    void testAddingBook() {
        bookService.addBook(AddBookRequest.builder()
                .isbn("1234567800")
                .title("Old Book")
                .bookType(BookType.PAPER_BOOK)
                .publicationDate(LocalDate.of(2015, 1, 1))
                .price(19.99)
                .stock(50)
                .build());
        System.out.println("Book added successfully: " + bookService.getBookById("1234567800").getTitle());

        bookService.addBook(AddBookRequest.builder()
                .isbn("1234567801")
                .title("Recent Book")
                .bookType(BookType.E_BOOK)
                .publicationDate(LocalDate.of(2023, 1, 1))
                .price(9.99)
                .stock(100)
                .build());
        System.out.println("Book added successfully: " + bookService.getBookById("1234567801").getTitle());

        bookService.addBook(AddBookRequest.builder()
                .isbn("1234567802")
                .title("Another Old Book")
                .bookType(BookType.PAPER_BOOK)
                .publicationDate(LocalDate.of(2010, 1, 1))
                .price(15.99)
                .stock(30)
                .build()
        );
        System.out.println("Book added successfully: " + bookService.getBookById("1234567802").getTitle());
    }

    void testDeletingBook() {
        String isbn = "1234567890";
        bookService.deleteBook(isbn);
        System.out.println("Book deleted successfully: " + isbn);
    }

    void testDeletingOutdatedBooks() {
        int years = 5;
        List<Book> deletedBooks = bookService.deleteOutDatedBooks(years);
        deletedBooks.forEach(book -> System.out.println("Book deleted successfully: " + book.getTitle()));
    }

    void testAddingAuthor() {
        authorService.addAuthor(new Author("John Doe", LocalDate.of(1980, 1, 1)));
        System.out.println("Author added successfully: " + authorService.getAuthorByName("John Doe").getName());
    }

    void testAddingAuthorWithBooks() {
        Author author = new Author("Jane Smith", LocalDate.of(1990, 5, 15));
        authorService.addAuthor(author);
        System.out.println("Author added successfully: " + author.getName());

        bookService.addBook(AddBookRequest.builder()
                .isbn("1234567899")
                .title("Jane's Book")
                .bookType(BookType.E_BOOK)
                .publicationDate(LocalDate.of(2022, 6, 1))
                .price(12.99)
                .stock(200)
                .authors(Set.of(author))
                .build());
        System.out.println("Book added successfully for author: " + author.getName());
    }

    void testAddUsers() {
        var request = AddUserRequest.builder()
                .name("testUser")
                .email("user@email.com")
                .password("password123")
                .role(Role.USER)
                .address("Test Address")
                .build();
        userService.addUser(request);
        System.out.println("User added successfully: " + userService.getUserByName("testUser").getName());
    }

    void testBuyingBook() {
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
