package pl.edu.lab2.spring_lab.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.lab2.spring_lab.entity.Genre;
import pl.edu.lab2.spring_lab.repository.GenreRepository;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class GenreDataLoader {

    @Bean
    CommandLineRunner loadGenres(GenreRepository genreRepository) {
        return args -> {
            if (genreRepository.count() == 0) {
                genreRepository.save(new Genre(
                        UUID.fromString("8b9fd235-1f26-4f01-bd59-dd54d78e5866"),
                        "Mystery",
                        70,
                        "Detective and thriller books"
                ));
                genreRepository.save(new Genre(
                        UUID.fromString("d1f9245f-33a4-4c21-b7b2-2a4d5f792f10"),
                        "Science Fiction",
                        85,
                        "Sci-fi books"
                ));
                genreRepository.save(new Genre(
                        UUID.fromString("e5a1f67a-2f3d-4f55-b8c3-1e8e7b5a9b22"),
                        "Fantasy",
                        90,
                        "Fantasy books"
                ));
            }
        };
    }
}

