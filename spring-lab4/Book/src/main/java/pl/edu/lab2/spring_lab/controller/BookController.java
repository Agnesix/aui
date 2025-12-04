package pl.edu.lab2.spring_lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.lab2.spring_lab.dto.*;
import pl.edu.lab2.spring_lab.entity.Book;
import pl.edu.lab2.spring_lab.repository.BookRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,RequestMethod.OPTIONS})
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping
    public List<BookCollectionDto> getAll() {
        return bookRepository.findAll().stream()
                .map(b -> {
                    BookCollectionDto dto = new BookCollectionDto();
                    dto.setId(b.getId());
                    dto.setTitle(b.getTitle());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookReadDto getOne(@PathVariable UUID id) {
        Book b = bookRepository.findById(id).orElseThrow();
        BookReadDto dto = new BookReadDto();
        dto.setId(b.getId());
        dto.setTitle(b.getTitle());
        dto.setAuthor(b.getAuthor());
        dto.setIsbn(b.getIsbn());
        dto.setPublicationYear(b.getPublicationYear());
        dto.setGenreId(b.getGenreId());
        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookReadDto create(@RequestBody BookCreateDto dto) {
        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());
        book.setGenreId(dto.getGenreId());
        Book saved = bookRepository.save(book);
        BookReadDto rd = new BookReadDto();
        rd.setId(saved.getId());
        rd.setTitle(saved.getTitle());
        rd.setAuthor(saved.getAuthor());
        rd.setIsbn(saved.getIsbn());
        rd.setPublicationYear(saved.getPublicationYear());
        rd.setGenreId(saved.getGenreId());
        return rd;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookReadDto update(@PathVariable UUID id, @RequestBody BookCreateDto dto) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());
        book.setGenreId(dto.getGenreId());
        Book saved = bookRepository.save(book);

        BookReadDto rd = new BookReadDto();
        rd.setId(saved.getId());
        rd.setTitle(saved.getTitle());
        rd.setAuthor(saved.getAuthor());
        rd.setIsbn(saved.getIsbn());
        rd.setPublicationYear(saved.getPublicationYear());
        rd.setGenreId(saved.getGenreId());
        return rd;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        bookRepository.deleteById(id);
    }

    @GetMapping("/by-genre/{genreId}")
    public List<BookCollectionDto> getByGenre(@PathVariable UUID genreId) {
        return bookRepository.findByGenreId(genreId).stream()
                .map(b -> {
                    BookCollectionDto dto = new BookCollectionDto();
                    dto.setId(b.getId());
                    dto.setTitle(b.getTitle());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}