package pl.edu.lab2.spring_lab.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookCollectionDto {
    private UUID id;
    private String title;
}
