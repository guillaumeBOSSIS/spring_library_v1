package fr.guillaumebossis.cefimtestcda2.entities.repository;

import fr.guillaumebossis.cefimtestcda2.entities.Book;
import fr.guillaumebossis.cefimtestcda2.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    Genre findByLibelleIgnoreCase(String libelle);
}
