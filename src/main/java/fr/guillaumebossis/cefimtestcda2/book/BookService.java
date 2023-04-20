package fr.guillaumebossis.cefimtestcda2.book;

import fr.guillaumebossis.cefimtestcda2.auteur.AuteurService;
import fr.guillaumebossis.cefimtestcda2.auteur.AuteurWithBooks;
import fr.guillaumebossis.cefimtestcda2.entities.Auteur;
import fr.guillaumebossis.cefimtestcda2.entities.Book;
import fr.guillaumebossis.cefimtestcda2.client.ClientService;
import fr.guillaumebossis.cefimtestcda2.entities.Genre;
import fr.guillaumebossis.cefimtestcda2.entities.State;
import fr.guillaumebossis.cefimtestcda2.entities.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Permet de définir une classe de Service contenant des traitements
@Service
public class BookService {

    @Autowired
    @Lazy
    private AuteurService auteurService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private BookRepository bookRepository;

    // SINON par constructeur si besoin que ce soit instancier vraiment dès le début
//    private ClientService clientService;
//    public BookService(ClientService clientService) {
//        this.clientService = clientService;
//    }

    private Auteur auteurTest = new Auteur("Tolkien", "JRR", 1920);
    private State stateTest = new State("Neuf");
    private Genre genreTest = new Genre("Policier");
    private List<Book> listBook = new ArrayList<>(){{
        add(new Book("Le petit prince", "Aventure", 12, auteurTest, stateTest, genreTest, false));
        add(new Book("1984", "Dystopique", 899, auteurTest, stateTest, genreTest, false));
        add(new Book("Le grand prince", "Aventure", 22, auteurTest, stateTest, genreTest, false));
    }};

    public String helloBook() {
        return "Hello Book !!!";
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByName(String name) {
        //return bookRepository.findAll().stream().filter(book -> book.getTitle().contains(name)).toList();
        return bookRepository.findByTitleContainingIgnoreCase(name).stream().toList();
        //return bookRepository.findByBooksName(name);
    }

    public List<Book> getBooksByGenre(int id) {
        return bookRepository.findByGenreId(id).stream().toList();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id);
    }

//    public Book saveBook(Book book) {
//        Book newBook = new Book(book.getTitle(), book.getDescription(), book.getNbPages(), book.getAuteur());
//        listBook.add(newBook);
////        Optional<Auteur> auteurs = auteurService.getAll().stream()
////                .filter(auteur -> auteurService.equalsAuteur(auteur, book.getAuteur()))
////                .findFirst();
////        if(auteurs.size() == 0) {
////            auteurService.getAll().add(book.getAuteur());
////        } else {
////            newBook.setAuteur(auteurs.get(0));
////        }
////        //TODO
//        return newBook;
//    }

    // Via List et classe métier
    public Book saveBookInList(Book newBook) throws InstanceAlreadyExistsException {
        Optional<Book> book = findBook(newBook);
        if (book.isPresent()){
            throw new InstanceAlreadyExistsException(String.valueOf(listBook.indexOf(newBook)));
        }
        listBook.add(newBook);
        return newBook;
    }

    // VIA BDD (Repository)
    public Book saveBook(Book newBook) throws EntityNotFoundException, InstanceAlreadyExistsException {
        //Auteur auteur = auteurService.findAuteur(newBook.getAuteur());
        Optional<Book> book = findBook(newBook);
//        if (auteur.isPresent()){
//            bookRepository.save(newBook);
//            return newBook;
//        }
        if (book.isPresent()){
            throw new InstanceAlreadyExistsException("Livre déjà existant");
        }
        bookRepository.save(newBook);
        return newBook;
        //throw new EntityNotFoundException("Auteur not found, please add it first");
    }

    public Optional<Book> findBook(Book bookToFind){
        //return listBook.stream().filter(book::equals).findFirst();
        return bookRepository.findAll().stream().filter(book -> book.getTitle().equals(bookToFind.getTitle())).findFirst();
    }

    public Book updateNbPages(int id, Integer newNbPages) {
        Book currentBook = bookRepository.findById(id);
        //Book currentBook = listBook.get(id);
        currentBook.setNbPages(newNbPages);
        bookRepository.save(currentBook);
        return currentBook;
    }

    public Book deleteBook(int id) {
        Book currentBook = bookRepository.findById(id);
        currentBook.setRemoved(true);
        bookRepository.save(currentBook);
        return currentBook;
    }

    public List<Book> getBooksForAuteur(int auteurId) {
        return bookRepository.findByAuteurId(auteurId);
    }
}
