package com.fawry.bookstore.request;

import com.fawry.bookstore.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class AddUserRequest {
    @NotNull
    private String name;

    @Email
    private String email;

    @NotNull
    @Length(min = 8)
    private String password;

    @NotNull
    private Role role;

    @NotNull
    private String address;
}
