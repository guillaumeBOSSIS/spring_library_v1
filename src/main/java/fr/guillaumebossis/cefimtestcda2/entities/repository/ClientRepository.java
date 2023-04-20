package fr.guillaumebossis.cefimtestcda2.entities.repository;

import fr.guillaumebossis.cefimtestcda2.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
