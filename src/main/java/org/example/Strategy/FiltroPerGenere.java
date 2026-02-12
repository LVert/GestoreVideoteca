package org.example.Strategy;

import org.example.domain.Film;
import org.example.domain.Genere;

public class FiltroPerGenere implements FiltraStrategy {

    private final Genere genereDaFiltrare;

    public FiltroPerGenere(Genere genere) {
        this.genereDaFiltrare = genere;
    }

    @Override
    public boolean filtra(Film film) {
        return film.getGenere() == genereDaFiltrare;
    }
}
