package com.fawry.bookstore.exception;

public class UnsupportedBookType extends RuntimeException {
    public UnsupportedBookType(String message) {
        super(message);
    }
}
