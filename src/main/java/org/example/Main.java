package org.example;

import org.example.domain.*;
import org.example.gui.VideotecaFrame;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Avvio dell'interfaccia grafica nel thread EDT
        SwingUtilities.invokeLater(() -> {

            CollezioneFilm collezione = new CollezioneFilm();

            // --- LOGICA DI CARICAMENTO AUTOMATICO (ENTRAMBI I FILE) ---
            caricaDatiAllAvvio(collezione);

            VideotecaFrame frame = new VideotecaFrame(collezione);
            frame.setVisible(true);
        });
    }

    /**
     * Carica i dati da videoteca.json E da videoteca.csv, unendoli.
     * Grazie a collezione.aggiungiFilm(), i duplicati vengono gestiti automaticamente.
     */
    private static void caricaDatiAllAvvio(CollezioneFilm collezione) {
        Path pathJson = Path.of("videoteca.json");
        Path pathCsv = Path.of("videoteca.csv");

        // 1. Tenta il caricamento dal file JSON
        if (Files.exists(pathJson)) {
            try {
                FilmRepository repoJson = new JsonFilmRepository("videoteca.json");
                List<Film> filmCaricati = repoJson.carica();
                for (Film f : filmCaricati) {
                    collezione.aggiungiFilm(f); // Usa aggiungi invece di caricaDaRepository per non pulire la lista
                }
                System.out.println("LOG: Importati " + filmCaricati.size() + " film da videoteca.json");
            } catch (IOException e) {
                System.err.println("LOG: Errore caricamento JSON: " + e.getMessage());
            }
        }

        // 2. Tenta il caricamento dal file CSV (senza sovrascrivere il JSON)
        if (Files.exists(pathCsv)) {
            try {
                FilmRepository repoCsv = new CsvFilmRepository("videoteca.csv");
                List<Film> filmCaricati = repoCsv.carica();
                int nuoviAggiunti = 0;
                for (Film f : filmCaricati) {
                    // aggiungiFilm ritorna true se il film è nuovo, false se era già presente
                    if (collezione.aggiungiFilm(f)) {
                        nuoviAggiunti++;
                    }
                }
                System.out.println("LOG: Importati " + nuoviAggiunti + " nuovi film da videoteca.csv (saltati duplicati).");
            } catch (IOException e) {
                System.err.println("LOG: Errore caricamento CSV: " + e.getMessage());
            }
        }

        if (!Files.exists(pathJson) && !Files.exists(pathCsv)) {
            System.out.println("LOG: Nessun file trovato. Collezione vuota.");
        }
    }
}