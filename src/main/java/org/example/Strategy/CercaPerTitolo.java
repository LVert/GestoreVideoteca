package org.example.Strategy;

import org.example.domain.Film;

public class CercaPerTitolo implements FiltraStrategy {

    private final String titolo;

    public CercaPerTitolo(String titolo){
        this.titolo = titolo.toLowerCase();
    }


    @Override
    public boolean filtra(Film film) {
        return film.getTitolo().toLowerCase().contains(titolo);
    }


}
