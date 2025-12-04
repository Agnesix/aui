package pl.edu.lab2.spring_lab.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookReadDto {
    private UUID id;
    private String title;
    private String author;
    private int isbn;
    private int publicationYear;
    private UUID genreId;}
