package fr.guillaumebossis.cefimtestcda2.entities.repository;

import fr.guillaumebossis.cefimtestcda2.entities.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuteurRepository extends JpaRepository<Auteur, Integer> {
}
