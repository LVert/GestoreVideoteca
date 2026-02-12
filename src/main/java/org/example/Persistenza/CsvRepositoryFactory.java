package org.example.Persistenza;

public class CsvRepositoryFactory extends RepositoryFactory {

    @Override
    public FilmRepository creaFilmRepository() {
        return new CsvFilmRepository("videoteca.csv");
    }


}
