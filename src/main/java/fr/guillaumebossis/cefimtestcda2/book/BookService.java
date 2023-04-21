package fr.guillaumebossis.cefimtestcda2.book;

import fr.guillaumebossis.cefimtestcda2.auteur.AuteurService;
import fr.guillaumebossis.cefimtestcda2.auteur.AuteurWithBooks;
import fr.guillaumebossis.cefimtestcda2.entities.Auteur;
import fr.guillaumebossis.cefimtestcda2.entities.Book;
import fr.guillaumebossis.cefimtestcda2.client.ClientService;
import fr.guillaumebossis.cefimtestcda2.entities.Genre;
import fr.guillaumebossis.cefimtestcda2.entities.State;
import fr.guillaumebossis.cefimtestcda2.entities.repository.BookRepository;
import fr.guillaumebossis.cefimtestcda2.entities.repository.GenreRepository;
import fr.guillaumebossis.cefimtestcda2.entities.repository.StateRepository;
import jakarta.persistence.EntityManager;
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
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private EntityManager entityManager;

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

    // TODO
    // VIA BDD (Repository)
    public Book saveBook(Book newBook) throws EntityNotFoundException, InstanceAlreadyExistsException {
        Auteur auteur = null;
        Genre genre = null;
        State state = null;
        Integer auteurId = null;
        Integer genreId = null;
        Integer stateId = null;

//        if (newBook.getAuteur() != null) {
//            auteur = auteurService.findAuteur(newBook.getAuteur());
//            auteurId = auteurService.getAuteurId(auteur);
//        }
//        if (newBook.getGenre() != null) {
//            genre = genreRepository.findAll().stream().filter(a -> a.getLibelle().equals(String.valueOf(newBook.getGenre())));
//            genreId = genreRepository.findByLibelleIgnoreCase(String.valueOf(newBook.getGenre())).getId();
//        }

        //Integer auteurId = auteurService.findAuteur(newBook.getAuteur()).getId();
//        int genreId = genreRepository.findByLibelle(newBook.getGenre().getLibelle().toString()).getId();
//        int stateId = stateRepository.findByLibelle(newBook.getState().getLibelle().toString()).getId();

        newBook.setAuteurId(2);
        newBook.setGenreId(1);
        newBook.setStateId(1);

        bookRepository.save(newBook);


////        Auteur auteur = auteurService.findAuteur(newBook.getAuteur());
//        Genre genreRef = entityManager.getReference(Genre.class, 1);;
////        if(newBook.getGenre() != null) {
////            //genre = genreRepository.findByLibelle(newBook.getGenre().getLibelle().toString());
////            genre = entityManager.getReference(Genre.class, 1);
////        }
////        State state = stateRepository.findByLibelle(newBook.getState().toString());
//        Optional<Book> book = findBook(newBook);
////        if (auteur.isPresent()){
////            bookRepository.save(newBook);
////            return newBook;
////        }
//        if (book.isPresent()){
//            throw new InstanceAlreadyExistsException("Livre déjà existant");
//        }
//
//
//        newBook.setGenre(genreRef);
//        //bookRepository.save(newBook);
//        entityManager.persist(newBook);
//        return newBook;
//        //throw new EntityNotFoundException("Auteur not found, please add it first");
//
//        newBook.setAuteurId(1);
//        bookRepository.save(newBook);
//        Genre newGenre = entityManager.getReference(Genre.class, "test");
//        Book newBook2 = entityManager.find(Book.class, 2);
////        newBook2.setGenre(newGenre);
////        bookRepository.save(newBook2);
////        (bookRepository.findById(newBook2.getId())).get().setGenre(newGenre);


        return newBook;



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
