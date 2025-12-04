package pl.edu.lab2.spring_lab.dto;

import lombok.Data;

import java.util.UUID;
import java.util.List;

@Data
public class GenreReadDto {
    private UUID id;
    private String name;
    private int popularity;
    private String description;
}
