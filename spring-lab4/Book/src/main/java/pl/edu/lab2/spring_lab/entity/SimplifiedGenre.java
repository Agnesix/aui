package pl.edu.lab2.spring_lab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "simplified_genres")
public class SimplifiedGenre {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id", columnDefinition = "CHAR(36)")
    private UUID parentId;
}
