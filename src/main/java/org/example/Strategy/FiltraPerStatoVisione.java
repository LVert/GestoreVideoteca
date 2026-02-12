package org.example.Strategy;

import org.example.domain.Film;
import org.example.domain.StatoVisione;

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
