package fr.guillaumebossis.cefimtestcda2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "auteur")
public class Auteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "date_naissance")
    private Integer dateNaissance;

    // select book.* from book inner join author on author.id = book.author_id;
    // @OneToMany
    // targetEntity : Class<?> de l'entité cible
    // mappedBy : Nom de l'attribut annoté de @ManyToOne
//    @OneToMany(targetEntity = Book.class, mappedBy = "auteur")
//    private List<Book> books;

    public Auteur() {}

    public Auteur(
            String nom,
            String prenom,
            Integer dateNaissance
    ) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Integer dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auteur auteur = (Auteur) o;
        return (Objects.equals(nom, auteur.nom) && Objects.equals(prenom, auteur.prenom) && Objects.equals(dateNaissance, auteur.dateNaissance));
    }

    // Entier pour gérer la notion d'unicité par exemple dans les Set ou les Map
    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
