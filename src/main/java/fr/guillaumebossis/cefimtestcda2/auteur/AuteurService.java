package fr.guillaumebossis.cefimtestcda2.auteur;

import fr.guillaumebossis.cefimtestcda2.book.BookService;
import fr.guillaumebossis.cefimtestcda2.entities.Auteur;
import fr.guillaumebossis.cefimtestcda2.entities.Book;
import fr.guillaumebossis.cefimtestcda2.entities.repository.AuteurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuteurService {

    @Autowired
    private AuteurRepository auteurRepository;

    @Autowired
    private BookService bookService;

    private List<Auteur> listAuteur = new ArrayList<>(){{
        add(new Auteur("Auteur", "Premier", 1));
        add(new Auteur("Auteur", "Deuxième", 2));
        add(new Auteur("de St Exupery", "Antoine", 1920));
        add(new Auteur("Orwell", "George", 1910));
    }};

    public String helloAuteur() {
        return "Hello Auteur !";
    }

    public boolean equalsAuteur(Auteur auteur1, Auteur auteur2) {
        return auteur1.equals(auteur2);
    }

    public List<Auteur> getAll() {
        return auteurRepository.findAll();
    }

    public Optional<Auteur> getById(int id) {
        return auteurRepository.findById(id);
    }

    public List<Auteur> getAuteursByName(String name) {
        return auteurRepository.findAll().stream()
                .filter(book -> book.getNom().contains(name))
                .toList();
    }

    public AuteurWithBooks getAuteurWithBooks(int auteurId){
        Optional<Auteur> author = auteurRepository.findById(auteurId);
        if (author.isPresent()){
            List<Book> listBooks = bookService.getBooksForAuteur(auteurId);
            return new AuteurWithBooks(author.get(), listBooks);
        }
        throw new EntityNotFoundException("Author with ID %d not found".formatted(auteurId));
    }

    public Auteur findAuteur(Auteur auteurToFind){
        Auteur auteur = auteurRepository.findAll().stream().filter(a -> a.equals(auteurToFind)).findFirst().orElse(null);
        if (auteur != null) {
            return auteur;
        }
        throw new EntityNotFoundException("Auteur not found");
        //return listBook.stream().filter(book::equals).findFirst();
        //return auteurRepository.findAll().stream().filter(auteur -> auteur.equals(auteurToFind)).findFirst();
    }

    public int getAuteurId(Auteur auteur){
        return auteurRepository.findAll().stream().filter(a -> a.equals(auteur)).findFirst().orElse(null).getId();
    }
}
