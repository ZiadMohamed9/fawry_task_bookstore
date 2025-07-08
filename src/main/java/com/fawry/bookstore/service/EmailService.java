package com.fawry.bookstore.service;

import com.fawry.bookstore.entity.Book;
import com.fawry.bookstore.entity.User;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendEmail(Book book, User user) {
        String email = user.getEmail();
        String bookTitle = book.getTitle();
        String message = "Dear " + user.getUsername() + ",\n\n" +
                         "Thank you for purchasing '" + bookTitle + "'.\n" +
                         "We hope you enjoy your reading experience!\n\n" +
                         "Best regards,\n" +
                         "Fawry Bookstore Team";

        System.out.println("Sending email to: " + email);
        System.out.println("Email content:\n" + message);
    }
}
