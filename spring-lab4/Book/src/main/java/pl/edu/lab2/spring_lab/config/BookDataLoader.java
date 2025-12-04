package pl.edu.lab2.spring_lab.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.lab2.spring_lab.entity.Book;
import pl.edu.lab2.spring_lab.entity.Genre;
import pl.edu.lab2.spring_lab.repository.BookRepository;
import pl.edu.lab2.spring_lab.repository.GenreRepository;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class BookDataLoader {

    @Bean
    CommandLineRunner loadElements(BookRepository bookRepository, GenreRepository genreRepository) {
        return args -> {
            if (genreRepository.count() == 0) {
                Genre mystery = new Genre(
                        UUID.fromString("8b9fd235-1f26-4f01-bd59-dd54d78e5866"),
                        "Mystery",
                        70,
                        "Detective and thriller books"
                );
                Genre scifi = new Genre(
                        UUID.fromString("d1f9245f-33a4-4c21-b7b2-2a4d5f792f10"),
                        "Science Fiction",
                        85,
                        "Sci-fi books"
                );
                genreRepository.save(mystery);
                genreRepository.save(scifi);
            }

        if (bookRepository.count() == 0) {

            // Dodajemy książki
            bookRepository.save(new Book(
                    UUID.fromString("cad5629a-9600-456d-aacf-f5a6c6a10a8e"),
                    412,
                    "Dune",
                    "Frank Herbert",
                    1965,
                    UUID.fromString("4275dd98-acc6-4429-8150-efa3aea6f973")
            ));
            bookRepository.save(new Book(
                    UUID.fromString("8b9fd235-1f26-4f01-bd59-dd54d78e5866"),
                    101,
                    "The Hound of the Baskervilles",
                    "Arthur Conan Doyle",
                    1902,
                    UUID.fromString("aaea9adf-5ea2-40a2-84f2-e6eaf97ecd10")
            ));
        }

        };
    }

}
