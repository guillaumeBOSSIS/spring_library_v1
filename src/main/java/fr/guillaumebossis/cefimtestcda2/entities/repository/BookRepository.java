package fr.guillaumebossis.cefimtestcda2.entities.repository;

import fr.guillaumebossis.cefimtestcda2.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    // Génération de requêtes via nommage de méthode : https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    List<Book> findByTitleContainingIgnoreCase(String name);

//    @Query("select b from Book b where UPPER(b.title) like UPPER(?1)")
//    List<Book> findByBooksName(String name);

    Book findById(int id);

    List<Book> findByAuteurId(int auteurId);

    List<Book> findByGenreId(int genreId);

}
