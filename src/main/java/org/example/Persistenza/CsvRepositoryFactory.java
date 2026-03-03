package org.example.Persistenza;


public class CsvRepositoryFactory extends RepositoryFactory {

    @Override
    public FilmRepository creaRepository() {
        return new CsvFilmRepository("videoteca.csv");
    }
}

