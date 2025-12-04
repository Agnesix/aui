package pl.edu.lab2.spring_lab.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "popularity")
    private int popularity;

    @Column(name = "description")
    private String description;

    // NOTE: removed books list — Books live in element-service
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return popularity == genre.popularity &&
                Objects.equals(name, genre.name) &&
                Objects.equals(description, genre.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, popularity);
    }
}
