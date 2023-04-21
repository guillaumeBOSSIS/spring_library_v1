package fr.guillaumebossis.cefimtestcda2.entities.repository;

import fr.guillaumebossis.cefimtestcda2.entities.Book;
import fr.guillaumebossis.cefimtestcda2.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Integer> {

    State findByLibelle(String libelle);
}
