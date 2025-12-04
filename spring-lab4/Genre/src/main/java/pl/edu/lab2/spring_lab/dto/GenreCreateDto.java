package pl.edu.lab2.spring_lab.dto;

import lombok.Data;

@Data
public class GenreCreateDto {
    private String name;
    private int popularity;
    private String description;
}
