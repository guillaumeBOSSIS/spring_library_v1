package fr.guillaumebossis.cefimtestcda2.entities;

import jakarta.persistence.*;

import java.util.Objects;

// Classe de données
// Bien penser à initialiser un constructeur vide + setter pour la désérialisation (CLIENT -> API)
// Bien penser à initialiser les getter pour la sérialisation (API -> CLIENT)
@Entity
@Table(name = "book")
public class Book {
    // @Id : Spécifie que l'attribut qui suit sera
    @Id
    // @GeneratedValue : Auto increment (IDENTITY) géré par le SGBD
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Définir le nom de la colonne à rattacher à l'attribut de la classe
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "nb_pages")
    private Integer nbPages;

    // Paramètre fetch de @ManyToOne
    // EAGER (default) : Récupération de l'auteur en même temps que le Book
    // LAZY : Récupération différé au moment où on accède à cet attribut
    @ManyToOne
    @JoinColumn(name = "auteur_id", updatable = true, insertable = true)
    private Auteur auteur;

    @Column(name = "is_removed")
    private boolean isRemoved;

    @ManyToOne
    @JoinColumn(name = "state_id", updatable = true, insertable = false)
    private State state;

    @ManyToOne
    @JoinColumn(name = "genre_id", updatable = true, insertable = false)
    private Genre genre;

    public Book() {}

    public Book(String title) {
        this(title, 0);
    }
    public Book(
            String title,
            String description,
            Integer nbPages,
            Auteur auteur,
            State state,
            Genre genre,
            boolean isRemoved
    ) {
        this.title = title;
        this.description = description;
        this.nbPages = nbPages;
        this.auteur = auteur;
        this.state = state;
        this.genre = genre;
        this.isRemoved = isRemoved;
    }

    public Book(String title, int i) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNbPages() {
        return nbPages;
    }

    public void setNbPages(Integer nbPages) {
        this.nbPages = nbPages;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    // Redéfinition de equals pour tester l'égalité entre 2 instances
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return (Objects.equals(title, book.title) && Objects.equals(auteur, book.auteur));
    }

    // Entier pour gérer la notion d'unicité par exemple dans les Set ou les Map
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
