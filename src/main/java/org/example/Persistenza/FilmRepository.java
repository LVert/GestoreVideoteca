package org.example.Persistenza;

import org.example.domain.Film;

import java.io.IOException;
import java.util.List;

public interface FilmRepository {

    // Salva l'intera collezione di film su memoria secondaria

    void salva(List<Film> films) throws IOException;

    // Carica tutti i film dalla memoria secondaria

    List<Film> carica() throws IOException;

}
