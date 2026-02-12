package org.example.Strategy;

import org.example.domain.Film;

import java.util.Comparator;

public interface OrdinamentoStrategy {

    Comparator<Film> ordina();
}
