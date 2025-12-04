package org.example.domain;

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
