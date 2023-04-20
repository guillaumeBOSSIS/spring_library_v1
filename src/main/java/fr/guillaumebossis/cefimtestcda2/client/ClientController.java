package fr.guillaumebossis.cefimtestcda2.client;

import fr.guillaumebossis.cefimtestcda2.entities.Book;
import fr.guillaumebossis.cefimtestcda2.entities.Client;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/api/client")
@Validated
public class ClientController {

    // Injection de notre classe de service
    @Autowired
    private ClientService clientService;

    // Création d'un point d'API : [GET] /api/client/all
    // On concatène le préfixe au point d'API spécifié
    @GetMapping("/all")
    public List<Client> getAllClients(){
        return clientService.getAll();
    }

    // Récupération d'un paramètre GET : /api/client/name?name=bossis
    // Dans ce cas, notre paramètre GET name aura pour valeur prince
    @GetMapping("/name")
    public List<Client> getClientsByName(@RequestParam String name){
        return clientService.getClientsByName(name);
    }

//    // Création d'un point d'API : [POST] /api/book
//    // @RequestBody va désérialiser le contenu JSON dans un format objet Java
//    @PostMapping("")
//    public ResponseEntity<Client> saveClientInList(@RequestBody Client newClient){
//        try {
//            return ResponseEntity.ok(clientService.saveClientInList(newClient));
//        } catch (InstanceAlreadyExistsException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(clientService.findClient(newClient).get());
//        }
//    }
//
//    @PatchMapping("/{id}/email")
//    public Book updateEmail(@PathVariable int id,
//                              @RequestBody
//                              @Min(value = 0, message = "Le nombre de pages doit être positif")
//                              String newEmail){
//        return clientService.updateEmail(id, newEmail);
//    }
//
//    @DeleteMapping("/{id}")
//    public Client deleteClient(@PathVariable int id){
//        return clientService.deleteClient(id);
//    }
}
