package fr.guillaumebossis.cefimtestcda2.client;

import fr.guillaumebossis.cefimtestcda2.book.StatsGenreLitteraire;
import fr.guillaumebossis.cefimtestcda2.entities.Book;
import fr.guillaumebossis.cefimtestcda2.entities.Client;
import fr.guillaumebossis.cefimtestcda2.entities.repository.AuteurRepository;
import fr.guillaumebossis.cefimtestcda2.entities.repository.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EntityManager entityManager;

//    private List<Client> listClient = new ArrayList<>(){{
//        add(new Client("Bossis", "Guillaume", 40, "guillaume_bossis@hotmail.com"));
//        add(new Client("Bossis Larousse", "Mathilda", 4, "test@email.com"));
//    }};

    public String helloClient() {
        return "Hello Client !";
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public List<Client> getClientsByName(String name) {
        return clientRepository.findAll().stream().filter(client -> client.getNom().contains(name)).toList();
        //return bookRepository.findByNameContainingIgnoreCase(name).stream().toList();
    }

    public List<StatsGenreLitteraire> getBestGenreForClient(Integer clientId){
        List<Tuple> clients = entityManager.createNativeQuery("""
                            select libelle, count(title) as nb_reservations
                            from client
                            inner join reservation on reservation.client_id = client.id
                            inner join book on book.id = reservation.book_id
                            inner join genre on book.genre_id = genre.id
                            where client.id = :clientId
                            group by libelle
                            order by nb_reservations DESC;
                        """, Tuple.class)
                .setParameter("clientId", clientId)
                .getResultList();

        return clients.stream().map(StatsGenreLitteraire::new).toList();
        //return clients.stream().map(tuple -> new StatsGenreLitteraire(tuple))
    }
}
