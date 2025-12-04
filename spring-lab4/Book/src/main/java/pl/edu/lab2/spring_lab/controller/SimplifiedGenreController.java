package pl.edu.lab2.spring_lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.lab2.spring_lab.entity.SimplifiedGenre;
import pl.edu.lab2.spring_lab.repository.SimplifiedGenreRepository;
import pl.edu.lab2.spring_lab.repository.BookRepository;
import pl.edu.lab2.spring_lab.entity.Book;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/simplified-genres")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class SimplifiedGenreController {

    private final SimplifiedGenreRepository simplifiedGenreRepository;
    private final BookRepository bookRepository;

    // Create or update simplified genre from category-service event
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrUpdate(@RequestBody SimplifiedGenre payload) {
        if (payload.getId() == null) {
            payload.setId(UUID.randomUUID());
        }
        simplifiedGenreRepository.save(payload);
    }

    // Delete simplified genre and its books (event from category-service)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id) {
        // delete related elements/books
        List<Book> books = bookRepository.findByGenreId(id);
        bookRepository.deleteAll(books);
        // delete simplified genre record
        simplifiedGenreRepository.findById(id).ifPresent(simplifiedGenreRepository::delete);
    }

    // Optional regular endpoints:
    @GetMapping
    public List<SimplifiedGenre> all() {
        return simplifiedGenreRepository.findAll();
    }
}
