package com.fawry.bookstore.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuyBookRequest {
    @NotNull
    private String isbn;

    @Min(1)
    private int quantity;

    @NotNull
    private String email;

    @NotNull
    private String address;
}
