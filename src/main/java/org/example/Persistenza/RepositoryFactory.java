package org.example.Persistenza;

import org.example.domain.TipoPersistenza;

public abstract class RepositoryFactory {

    public abstract FilmRepository creaRepository();

    public static RepositoryFactory getFactory(TipoPersistenza tipo) {
        switch (tipo) {
            case CSV:
                return new CsvRepositoryFactory();
            case JSON:
                return new JsonRepositoryFactory();
            default:
                throw new IllegalArgumentException("Tipo di persistenza non supportato: " + tipo);
        }
    }
}

