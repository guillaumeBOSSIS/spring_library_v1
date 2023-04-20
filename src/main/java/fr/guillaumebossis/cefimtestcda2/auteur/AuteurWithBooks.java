package fr.guillaumebossis.cefimtestcda2.auteur;

import fr.guillaumebossis.cefimtestcda2.entities.Auteur;
import fr.guillaumebossis.cefimtestcda2.entities.Book;

import java.util.List;

public class AuteurWithBooks {
    private Auteur auteur;
    private List<Book> books;

    public AuteurWithBooks() {
    }

    public AuteurWithBooks(Auteur auteur, List<Book> books) {
        this.auteur = auteur;
        this.books = books;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
