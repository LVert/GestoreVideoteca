package org.example.domain;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;



public class CsvFilmRepository implements FilmRepository{

    private final Path filePath;

    public CsvFilmRepository(String fileName) {
        this.filePath = Path.of(fileName);
    }


    @Override
    public void salva(List<Film> films) throws IOException {
        System.out.println("Tentativo di salvataggio avviato...");
        System.out.println("Numero di film ricevuti dal repository: " + films.size());
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            for (Film f : films) {
                System.out.println("Scrittura film: " + f.getTitolo());
                String line = String.join(";",
                        escape(f.getTitolo()),
                        escape(f.getRegista()),
                        String.valueOf(f.getAnnoUscita()),
                        f.getGenere().name(),
                        String.valueOf(f.getValutazionePersonale()),
                        f.getStatoVisione().name()
                );
                writer.write(line);
                writer.newLine();
            }
        }
    }

    @Override
    public List<Film> carica() throws IOException {
        List<Film> result = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return result; // nessun file: collezione vuota
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (parts.length != 6) {
                    continue; // riga malformata, la salto
                }

                String titolo = unescape(parts[0]);
                String regista = unescape(parts[1]);
                int anno = Integer.parseInt(parts[2]);
                Genere genere = Genere.valueOf(parts[3]);
                int valutazione = Integer.parseInt(parts[4]);
                StatoVisione stato = StatoVisione.valueOf(parts[5]);

                Film film = new FilmBuilder()
                        .titolo(titolo)
                        .regista(regista)
                        .annoUscita(anno)
                        .genere(genere)
                        .valutazione(valutazione)
                        .stato(stato)
                        .build();

                result.add(film);
            }
        }

        return result;
    }

    // Piccola utilità per gestire eventuali ; nel testo, dato che il formato riga è:
    // titolo;regista;anno;genere;valutazione;stato

    private String escape(String s) {
        return s.replace(";", "\\;");
    }

    private String unescape(String s) {
        return s.replace("\\;", ";");
    }

}
