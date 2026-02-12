package org.example.Persistenza;

public class JsonRepositoryFactory extends RepositoryFactory {


    @Override
    public FilmRepository creaFilmRepository() {
        return new JsonFilmRepository("videoteca.json");
    }
}
