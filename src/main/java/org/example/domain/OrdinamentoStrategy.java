package org.example.domain;

import java.util.Comparator;

public interface OrdinamentoStrategy {

    Comparator<Film> ordina();
}
