package com.fawry.bookstore.service;

import com.fawry.bookstore.entity.Author;
import com.fawry.bookstore.repository.AuthorRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with ID: " + id));
    }

    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Author not found with name: " + name));
    }

    @Transactional
    public void addAuthor(Author author) {
        authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    public void updateAuthor(Long id, Author author) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with ID: " + id));

        existingAuthor.setName(author.getName());

        authorRepository.save(existingAuthor);
    }
}
