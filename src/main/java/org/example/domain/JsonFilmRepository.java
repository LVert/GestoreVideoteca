package org.example.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonFilmRepository implements FilmRepository{
    private final Path filePath;
    private final Gson gson;

    public JsonFilmRepository(String fileName) {
        this.filePath = Path.of(fileName);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void salva(List<Film> films) throws IOException {

        List<FilmDTO> dtoList = films.stream()
                .map(FilmDTO::fromFilm)
                .toList();

        try (Writer writer = Files.newBufferedWriter(filePath)) {
            gson.toJson(dtoList, writer);
        }
    }

    @Override
    public List<Film> carica() throws IOException {

        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        Type listType = new TypeToken<List<FilmDTO>>() {}.getType();

        try (Reader reader = Files.newBufferedReader(filePath)) {
            List<FilmDTO> dtoList = gson.fromJson(reader, listType);
            if (dtoList == null) {
                return new ArrayList<>();
            }

            List<Film> result = new ArrayList<>();
            for (FilmDTO dto : dtoList) {
                result.add(dto.toFilm());
            }
            return result;
        }
    }

    //DTO interno
    private static class FilmDTO {
        String titolo;
        String regista;
        int annoUscita;
        String genere;
        int valutazione;
        String statoVisione;

        static FilmDTO fromFilm(Film film) {
            FilmDTO dto = new FilmDTO();
            dto.titolo = film.getTitolo();
            dto.regista = film.getRegista();
            dto.annoUscita = film.getAnnoUscita();
            dto.genere = film.getGenere().name();
            dto.valutazione = film.getValutazionePersonale();
            dto.statoVisione = film.getStatoVisione().name();
            return dto;
        }

        Film toFilm() {
            return new FilmBuilder()
                    .titolo(titolo)
                    .regista(regista)
                    .annoUscita(annoUscita)
                    .genere(Genere.valueOf(genere))
                    .valutazione(valutazione)
                    .stato(StatoVisione.valueOf(statoVisione))
                    .build();
        }
    }
}
