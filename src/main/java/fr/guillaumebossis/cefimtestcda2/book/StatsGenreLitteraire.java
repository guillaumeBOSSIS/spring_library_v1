package fr.guillaumebossis.cefimtestcda2.book;

import jakarta.persistence.Tuple;

public class StatsGenreLitteraire {
    public String genre;
    public Integer nbReservations;

    public StatsGenreLitteraire(Tuple tuple) {
        this.genre = tuple.get(0, String.class);
        // this.genre = tuple.get("nom_genre", String.class);
        this.nbReservations = tuple.get(1, Integer.class);
        // this.nbReservations = tuple.get("nb_reservations", String.class);
    }

    public StatsGenreLitteraire() {
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getNbReservations() {
        return nbReservations;
    }

    public void setNbReservations(Integer nbReservations) {
        this.nbReservations = nbReservations;
    }
}
