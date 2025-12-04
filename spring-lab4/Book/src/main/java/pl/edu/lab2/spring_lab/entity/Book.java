package pl.edu.lab2.spring_lab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)", nullable = false)
    private UUID id;

    @Column(unique = true, name = "isbn")
    private int isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publication_year")
    private int publicationYear;

    // REPLACED relation with simple genreId (keeping UUID)
    @Column(name = "genre_id", columnDefinition = "CHAR(36)")
    private UUID genreId;

    public int compareTo(Book o) {
        int cmp = this.title.compareToIgnoreCase(o.title);
        if (cmp != 0) return cmp;
        return Integer.compare(this.publicationYear, o.publicationYear);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return isbn == book.isbn &&
                publicationYear == book.publicationYear &&
                Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, isbn, publicationYear);
    }
}
