package org.example.domain;

import java.util.Objects;

public class Film {

    private String titolo;
    private String regista;
    private int annoUscita;
    private Genere genere;
    private int valutazionePersonale; // 1-5
    private StatoVisione statoVisione;

    private Film(FilmBuilder builder) {
        this.titolo = builder.titolo;
        this.regista = builder.regista;
        this.annoUscita = builder.annoUscita;
        this.genere = builder.genere;
        this.valutazionePersonale = builder.valutazionePersonale;
        this.statoVisione = builder.statoVisione;
    }

    // Costruttore package-private per la deserializzazione JSON (usato da Gson)
    Film() {
    }

    public static class FilmBuilder{
        private String titolo;
        private String regista;
        private Integer annoUscita;
        private Genere genere;
        private Integer valutazionePersonale;
        private StatoVisione statoVisione;

        public FilmBuilder titolo(String titolo) {
            if (titolo == null || titolo.isBlank())
                throw new IllegalArgumentException("Il titolo non può essere vuoto.");
            this.titolo = titolo;
            return this;
        }

        public FilmBuilder regista(String regista) {
            if (regista == null || regista.isBlank())
                throw new IllegalArgumentException("Il regista non può essere vuoto.");
            this.regista = regista;
            return this;
        }

        public FilmBuilder annoUscita(int anno) {
            if (anno < 1888)
                throw new IllegalArgumentException("Anno di uscita non valido.");
            this.annoUscita = anno;
            return this;
        }

        public FilmBuilder genere(Genere genere) {
            if (genere == null) {
                throw new IllegalArgumentException("Il genere non può essere null.");
            }
            this.genere = genere;
            return this;
        }

        public FilmBuilder valutazione(int voto) {
            if (voto < 1 || voto > 5)
                throw new IllegalArgumentException("La valutazione deve essere tra 1 e 5.");
            this.valutazionePersonale = voto;
            return this;
        }

        public FilmBuilder stato(StatoVisione stato) {
            if (stato == null) {
                throw new IllegalArgumentException("Lo stato di visione non può essere null.");
            }
            this.statoVisione = stato;
            return this;
        }


        public Film build() {

            if (titolo == null)
                throw new IllegalStateException("Titolo non impostato nel builder.");

            if (regista == null)
                throw new IllegalStateException("Regista non impostato nel builder.");

            if (annoUscita == null)
                throw new IllegalStateException("Anno di uscita non valido");

            if (genere == null)
                throw new IllegalStateException("Genere non impostato nel builder.");

            if (valutazionePersonale == null)
                throw new IllegalStateException("Valutazione personale non impostata nel builder.");

            if (statoVisione == null)
                throw new IllegalStateException("Stato di visione non impostato nel builder.");

            return new Film(this);
        }
    }

    public String getTitolo() {
        return titolo;
    }

    public String getRegista() {
        return regista;
    }

    public int getAnnoUscita() {
        return annoUscita;
    }

    public Genere getGenere() {
        return genere;
    }

    public int getValutazionePersonale() {
        return valutazionePersonale;
    }

    public StatoVisione getStatoVisione() {
        return statoVisione;
    }

    // equals/hashCode basati su titolo + regista

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film film = (Film) o;
        return Objects.equals(titolo, film.titolo) &&
                Objects.equals(regista, film.regista);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titolo, regista);
    }
}

