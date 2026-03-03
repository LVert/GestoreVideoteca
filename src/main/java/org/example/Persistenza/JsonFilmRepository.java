package org.example.Persistenza;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.domain.Film;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonFilmRepository implements FilmRepository {
    private final Path filePath;
    private final Gson gson;

    public JsonFilmRepository(String fileName) {
        this.filePath = Path.of(fileName);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void salva(List<Film> films) throws IOException {
        try (Writer writer = Files.newBufferedWriter(filePath)) {
            gson.toJson(films, writer);
        }
    }

    @Override
    public List<Film> carica() throws IOException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        Type listType = new TypeToken<List<Film>>() {}.getType();

        try (Reader reader = Files.newBufferedReader(filePath)) {
            List<Film> films = gson.fromJson(reader, listType);
            return films != null ? films : new ArrayList<>();
        }
    }
}
