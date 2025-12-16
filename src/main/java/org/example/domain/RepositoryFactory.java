package org.example.domain;

public abstract class RepositoryFactory {

    public abstract FilmRepository creaFilmRepository();

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
