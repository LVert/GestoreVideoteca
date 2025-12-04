package org.example.domain;

public class FiltraPerStatoVisione implements FiltraStrategy {

    private final StatoVisione statoDaFiltrare;

    public FiltraPerStatoVisione(StatoVisione statoDaFiltrare) {
        this.statoDaFiltrare = statoDaFiltrare;
    }

    @Override
    public boolean filtra(Film film) {
        return film.getStatoVisione() == statoDaFiltrare;
    }
}
