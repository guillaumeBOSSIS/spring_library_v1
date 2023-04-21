package fr.guillaumebossis.cefimtestcda2.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.guillaumebossis.cefimtestcda2.auteur.AuteurService;
import fr.guillaumebossis.cefimtestcda2.entities.Auteur;
import fr.guillaumebossis.cefimtestcda2.entities.Book;
import fr.guillaumebossis.cefimtestcda2.entities.Genre;
import fr.guillaumebossis.cefimtestcda2.entities.State;
import fr.guillaumebossis.cefimtestcda2.entities.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// Annotation pour préciser que cette classe contient des tests
@SpringBootTest
// Permet au MockMvc de se configurer pour attaquer notre API
@AutoConfigureMockMvc
public class BookTests {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuteurService auteurService;
    @Autowired
    private GenreRepository genreRepository;

    // Classe pour simuler des appels REST
    @Autowired
    private MockMvc mockMvc;

    // Classe de sérialisation / désérialisation
    @Autowired
    private ObjectMapper objectMapper;


    Auteur auteurTest = new Auteur("de Saint-Exupery", "Antoine", 1930);
    State stateTest = new State("Neuf");
    Genre genreTest = new Genre("Aventure");
    Book bookTest = new Book("Le petit prince", "Aventure", 55, auteurTest, stateTest, genreTest, false);

    @Test
    void testHelloBook() {
        assert bookService.helloBook().equals(("Hello Book !!!"));
    }

    @Test
    void testGetAllBook(){
        Auteur auteur = new Auteur("de Saint-Exupery", "Antoine", 1930);
        assert bookService.getAll().contains(bookTest);
    }

    @Test
    void testGetAllBookByAPI() throws Exception {
        Auteur auteur = new Auteur("de Saint-Exupery", "Antoine", 1930);
        // Création de notre requête au moyen de la classe MockMvcRequestBuilders
        // Utilisation de la méthode correspondant au verbe HTTP voulu, qui prend en paramètre l'URL du point d'API
        RequestBuilder request = MockMvcRequestBuilders.get("/api/book/all");
        // Test du status de la réponse, ici 200 (isOk())
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        // Désérialisation du contenu de la réponse en List<Book>
        List<Book> books = Arrays.asList(objectMapper.readValue(contentAsString, Book[].class));
        // OU
        List<Book> books2 = objectMapper.readValue(contentAsString, new TypeReference<>() {});
        // Pour un seul livre en réponse
        // Book oneBook = objectMapper.readValue(contentAsString, Book.class);
        assert books.contains(bookTest);
    }

    @Test
    void testGetBookPrince() throws Exception {
        Auteur auteur = new Auteur("Rowling", "J. K.", 1960);
        RequestBuilder request = MockMvcRequestBuilders.get("/api/book/name?name=Prince");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Book> books = Arrays.asList(objectMapper.readValue(contentAsString, Book[].class));
        assert books.contains(bookTest);
    }

    @Test
    void testPostBookWithExistingAuteur() throws Exception {
        //TODO ne passe pas

        // Pour envoyer un livre, bien penser à indiquer le type du contenu du body (JSON = .contentType(MediaType.APPLICATION_JSON)
        // Et à serialiser le contenu (.content(objectMapper.writeValueAsString(book)))
        Auteur auteur = new Auteur("Rowling", "J. K.", 1960);
        //Auteur auteur = auteurService.findAuteur(auteurToFind);
        //int auteurId = auteur.getId();
        //Auteur existingAuteur = auteurService.getById(auteur.getId());
        Book book = new Book("Harry Potter tome 3", "Magicien", 250, null, null, genreTest, false);

        bookService.saveBook(book);
        RequestBuilder request = MockMvcRequestBuilders.post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Book newBook = objectMapper.readValue(contentAsString, Book.class);
        assert newBook.equals(book);
    }

    @Test
    void testPostBookFailed() throws Exception {
        //Book book = bookService.getAll().get(0);
        Book book = bookService.getAll().get(1);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isConflict();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Book newBook = objectMapper.readValue(contentAsString, Book.class);
        assert newBook.equals(book);
    }

    @Test
    void testPatchBook() throws Exception {
        int firstBookNbPages = bookService.getAll().get(0).getNbPages().intValue();
        Integer newNbPages = firstBookNbPages + 1;

        RequestBuilder request = MockMvcRequestBuilders.patch("/api/book/1/pages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNbPages.toString());

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request).andExpect(resultStatus);

        Book newBook = bookService.getAll().get(0);
        assert !Objects.equals(newBook.getNbPages(), firstBookNbPages);
        assert newBook.getNbPages().equals(newNbPages);
    }

//    @Test
//    void testPatchBookWrong() throws Exception {
//        int firstBookNbPages = bookService.getAll().get(0).getNbPages().intValue();
//        Integer newNbPages = -100;
//
//        RequestBuilder request = MockMvcRequestBuilders.patch("/api/book/0/pages").contentType(MediaType.APPLICATION_JSON).content(newNbPages.toString());
//
//        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
//        mockMvc.perform(request).andExpect(resultStatus);
//
//        Book newBook = bookService.getAll().get(0);
//        assert !Objects.equals(newBook.getNbPages(), firstBookNbPages);
//        assert newBook.getNbPages().equals(newNbPages);
//    }

//    @Test
//    void testDeleteBook() throws Exception {
//        int nbBooks = bookService.getAll().size();
//
//        RequestBuilder request = MockMvcRequestBuilders.delete("/api/book/1");
//        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
//        mockMvc.perform(request).andExpect(resultStatus);
//
//        List<Book> listBooksAfterDelete = bookService.getAll();
//        assert nbBooks - 1 == listBooksAfterDelete.size();
//    }

    @Test
    @Transactional
    void testDeleteBook() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/book/1");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request).andExpect(resultStatus);

        Book bookRemoved = bookService.getBookById(1);
        assert bookRemoved.isRemoved();
    }

    @Test
    void testGetBooksWithGenre() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/book/genre/3");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Book> books = Arrays.asList(objectMapper.readValue(contentAsString, Book[].class));
        assert books.contains(bookTest);
    }

//    @Test
//    void testFindGenreByLibelle() {
//        Genre genreTest = new Genre("Aventure");
//        assert genreRepository.findByLibelleIgnoreCase(String.valueOf(genreTest)) == ;
//    }

}
