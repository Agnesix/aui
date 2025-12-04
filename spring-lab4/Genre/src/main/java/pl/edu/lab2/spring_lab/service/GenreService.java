package pl.edu.lab2.spring_lab.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.lab2.spring_lab.dto.*;
import pl.edu.lab2.spring_lab.entity.Genre;
import pl.edu.lab2.spring_lab.repository.GenreRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private final String ELEMENTS_SERVICE_BASE = "http://localhost:8082"; // direct; gateway tests later use 8080

    public List<GenreCollectionDto> findAllCollection() {
        return genreRepository.findAll().stream()
                .map(this::toCollectionDTO)
                .collect(Collectors.toList());
    }

    public GenreReadDto findById(UUID id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));
        return toReadDTO(genre);
    }

    @Transactional
    public GenreReadDto create(GenreCreateDto dto) {
        Genre genre = new Genre();
        genre.setId(UUID.randomUUID());
        genre.setName(dto.getName());
        genre.setPopularity(dto.getPopularity());
        genre.setDescription(dto.getDescription());
        Genre saved = genreRepository.save(genre);

        // Notify element-service to create simplified genre record
        try {
            restTemplate.postForLocation(ELEMENTS_SERVICE_BASE + "/api/simplified-genres", toSimplifiedGenreDto(saved));
        } catch (Exception ex) {
            ex.printStackTrace(); // for lab: log error but don't fail create
        }

        return toReadDTO(saved);
    }

    @Transactional
    public GenreReadDto update(UUID id, GenreCreateDto dto) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));
        genre.setName(dto.getName());
        genre.setPopularity(dto.getPopularity());
        genre.setDescription(dto.getDescription());
        Genre saved = genreRepository.save(genre);

        // Optionally notify element-service about update (not required by lab, but ok)
        try {
            restTemplate.postForLocation(ELEMENTS_SERVICE_BASE + "/api/simplified-genres", toSimplifiedGenreDto(saved));
        } catch (Exception ex) { ex.printStackTrace(); }

        return toReadDTO(saved);
    }

    @Transactional
    public void delete(UUID id) {
        if (!genreRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found");
        }
        genreRepository.deleteById(id);

        // Notify element-service to delete simplified genre and cascade-delete elements
        try {
            restTemplate.delete(ELEMENTS_SERVICE_BASE + "/api/simplified-genres/" + id.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // DTO conversions (lightweight)
    private GenreReadDto toReadDTO(Genre genre) {
        GenreReadDto dto = new GenreReadDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setPopularity(genre.getPopularity());
        dto.setDescription(genre.getDescription());
        return dto;
    }

    private GenreCollectionDto toCollectionDTO(Genre genre) {
        GenreCollectionDto dto = new GenreCollectionDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }

    private SimplifiedGenreDto toSimplifiedGenreDto(Genre g) {
        SimplifiedGenreDto d = new SimplifiedGenreDto();
        d.setId(g.getId());
        d.setName(g.getName());
        // parentId not used here; set null
        d.setParentId(null);
        return d;
    }
}
