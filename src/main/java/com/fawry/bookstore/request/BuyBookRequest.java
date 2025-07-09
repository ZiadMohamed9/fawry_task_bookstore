package com.fawry.bookstore.request;

import com.fawry.bookstore.entity.Book;
import com.fawry.bookstore.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuyBookRequest {
    @NotNull
    private Book book;

    @NotNull
    private User user;

    @Min(0)
    private int quantity;
}
