package com.fawry.bookstore.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String address;

    public User(String username, String email, String password, Role role, String address) {
        this.name = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.address = address;
    }
}
