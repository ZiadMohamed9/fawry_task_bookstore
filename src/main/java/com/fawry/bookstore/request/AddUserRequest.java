package com.fawry.bookstore.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddUserRequest {
    @NotNull
    private String username;

    @Email
    private String email;

    @NotNull
    @Length(min = 8)
    private String password;

    @NotNull
    private String role;

    @NotNull
    private String address;
}
