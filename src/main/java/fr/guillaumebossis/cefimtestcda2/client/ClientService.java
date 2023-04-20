package fr.guillaumebossis.cefimtestcda2.client;

import fr.guillaumebossis.cefimtestcda2.entities.Book;
import fr.guillaumebossis.cefimtestcda2.entities.Client;
import fr.guillaumebossis.cefimtestcda2.entities.repository.AuteurRepository;
import fr.guillaumebossis.cefimtestcda2.entities.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

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
}
