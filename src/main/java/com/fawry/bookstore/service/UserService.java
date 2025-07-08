package com.fawry.bookstore.service;

import com.fawry.bookstore.entity.User;
import com.fawry.bookstore.repository.UserRepository;
import com.fawry.bookstore.request.AddUserRequest;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User getUserByName(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public void getUserById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public void addUser(@Valid AddUserRequest request) {
        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getRole(),
                request.getAddress()
        );
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(Long id, @Valid AddUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setAddress(request.getAddress());

        userRepository.save(user);
    }
}
