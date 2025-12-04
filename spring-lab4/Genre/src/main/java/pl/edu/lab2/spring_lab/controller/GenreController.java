package pl.edu.lab2.spring_lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.lab2.spring_lab.dto.*;
import pl.edu.lab2.spring_lab.service.GenreService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/genres")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,RequestMethod.OPTIONS})
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<GenreCollectionDto> getAll() {
        return genreService.findAllCollection();
    }

    @GetMapping("/{id}")
    public GenreReadDto getOne(@PathVariable UUID id) {
        return genreService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreReadDto create(@RequestBody GenreCreateDto dto) {
        return genreService.create(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        genreService.delete(id);
    }

    @PutMapping("/{id}")
    public GenreReadDto update(@PathVariable UUID id, @RequestBody GenreCreateDto dto) {
        return genreService.update(id, dto);
    }

}
