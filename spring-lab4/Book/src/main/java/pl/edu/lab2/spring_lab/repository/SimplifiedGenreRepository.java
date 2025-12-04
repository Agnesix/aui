package pl.edu.lab2.spring_lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.lab2.spring_lab.entity.SimplifiedGenre;

import java.util.UUID;

@Repository
public interface SimplifiedGenreRepository extends JpaRepository<SimplifiedGenre, UUID> { }
