package pl.edu.lab2.spring_lab.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class GenreCollectionDto {
    private UUID id;
    private String name;
}
