package org.example.Persistenza;

public class JsonRepositoryFactory extends RepositoryFactory {

    @Override
    public FilmRepository creaRepository() {
        return new JsonFilmRepository("videoteca.json");
    }
}

