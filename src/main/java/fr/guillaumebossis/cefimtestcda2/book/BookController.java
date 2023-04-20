package fr.guillaumebossis.cefimtestcda2.book;

import fr.guillaumebossis.cefimtestcda2.entities.Book;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

// Annotation pour définir un nouveau controller (qui va contenir une liste de points d'API)
@RestController
// Ajout d'un préfixe à tout nos points d'API
@RequestMapping("/api/book")
// Penser à la mettre pour activer les validations par annotations
@Validated
public class BookController {

    // Injection de notre classe de service
    @Autowired
    private BookService bookService;

    // Création d'un point d'API : [GET] /api/book/all
    // On concatène le préfixe au point d'API spécifié
    @GetMapping("/all")
    public List<Book> getAllBooks(){
        return bookService.getAll();
    }

    // Récupération d'un paramètre GET : /api/book/name?name=prince
    // Dans ce cas, notre paramètre GET name aura pour valeur prince
    @GetMapping("/name")
    public List<Book> getBooksByName(@RequestParam String name){
        return bookService.getBooksByName(name);
    }

    @GetMapping("/genre/{id}")
    public List<Book> getBooksByGenre(@PathVariable int id){
        return bookService.getBooksByGenre(id);
    }

    // Création d'un point d'API : [POST] /api/book
    // @RequestBody va désérialiser le contenu JSON dans un format objet Java
//    @PostMapping("")
//    public ResponseEntity<Book> saveBookInList(@RequestBody Book newBook){
//        try {
//            return ResponseEntity.ok(bookService.saveBookInList(newBook));
//        } catch (InstanceAlreadyExistsException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(bookService.findBook(newBook).get());
//        }
//    }

    @PostMapping("")
    public ResponseEntity<Book> saveBook(@RequestBody Book newBook){
        try {
            return ResponseEntity.ok(bookService.saveBook(newBook));
        } catch (InstanceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(bookService.findBook(newBook).get());
        }
    }

    @PatchMapping("/{id}/pages")
    public Book updateNbPages(@PathVariable int id,
                              @RequestBody
                              @Min(value = 0, message = "Le nombre de pages doit être positif")
                              Integer newNbPages){
        return bookService.updateNbPages(id, newNbPages);
    }

    @DeleteMapping("/{id}")
    public Book deleteBook(@PathVariable int id){
        return bookService.deleteBook(id);
    }
}
