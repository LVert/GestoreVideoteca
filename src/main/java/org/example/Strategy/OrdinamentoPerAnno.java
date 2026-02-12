package org.example.Strategy;

import org.example.domain.Film;

import java.util.Comparator;

public class OrdinamentoPerAnno implements OrdinamentoStrategy {

    @Override
    public Comparator<Film> ordina() {
        return Comparator.comparingInt(Film::getAnnoUscita);
    }
}
