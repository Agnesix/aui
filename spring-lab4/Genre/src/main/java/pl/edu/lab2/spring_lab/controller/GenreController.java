package pl.edu.lab2.spring_lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.lab2.spring_lab.dto.GenreCollectionDto;
import pl.edu.lab2.spring_lab.dto.GenreCreateDto;
import pl.edu.lab2.spring_lab.dto.GenreReadDto;
import pl.edu.lab2.spring_lab.service.GenreService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/genres")
//@CrossOrigin(
//        origins = "http://localhost:4200",
//        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
//                RequestMethod.DELETE, RequestMethod.OPTIONS}
//)
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    // GET /api/genres – lista kategorii
    @GetMapping
    public List<GenreCollectionDto> getAll() {
        return genreService.findAllCollection();
    }

    // GET /api/genres/{id} – szczegóły
    @GetMapping("/{id}")
    public GenreReadDto getOne(@PathVariable UUID id) {
        return genreService.findById(id);
    }

    // POST /api/genres – dodawanie
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreReadDto create(@RequestBody GenreCreateDto dto) {
        return genreService.create(dto);
    }

    // DELETE /api/genres/{id} – usuwanie
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        genreService.delete(id);
    }

    // PUT /api/genres/{id} – edycja
    @PutMapping("/{id}")
    public GenreReadDto update(@PathVariable UUID id, @RequestBody GenreCreateDto dto) {
        return genreService.update(id, dto);
    }
}
