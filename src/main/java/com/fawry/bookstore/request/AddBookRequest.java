package com.fawry.bookstore.request;

import com.fawry.bookstore.entity.BookType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AddBookRequest {
    @NotNull
    private String isbn;

    @NotNull
    private String title;

    @NotNull
    private BookType bookType;

    @NotNull
    private LocalDate publicationDate;

    @Min(0)
    private double price;

    @Min(0)
    private int stock;
}
