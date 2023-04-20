package fr.guillaumebossis.cefimtestcda2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.Objects;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "age")
    private Integer age;

    @Email
    @Column(name = "email")
    private String email;

    //TODO
//    @Column(name = "book_list")
//    private Integer bookList;
    //private List<Book> bookList;

    public Client() {}

    public Client(
            String nom,
            String prenom,
            int age,
            String email
    ) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
    }
//    public Client(
//            String nom,
//            String prenom,
//            int age,
//            List bookList
//    ) {
//        this.nom = nom;
//        this.prenom = prenom;
//        this.age = age;
//        this.bookList = bookList;
//    }

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

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //    public List getBookList() {
//        return bookList;
//    }
//
//    public void setBookList(List bookList) {
//        this.bookList = bookList;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return (Objects.equals(nom, client.nom) && Objects.equals(prenom, client.prenom) && Objects.equals(age, client.age));
    }
}
