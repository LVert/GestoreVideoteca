package org.example.Strategy;

import org.example.domain.Film;

import java.util.Comparator;

public class OrdinamentoPerValutazione implements OrdinamentoStrategy {

    @Override
    public Comparator<Film> ordina() {
         return Comparator.comparing(Film::getValutazionePersonale);
    }
}
