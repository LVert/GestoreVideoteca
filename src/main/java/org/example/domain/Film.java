package org.example.domain;

import java.util.Objects;

public class Film {

    private String titolo;
    private String regista;
    private int annoUscita;
    private Genere genere;
    private int valutazionePersonale; // 1-5
    private StatoVisione statoVisione;

    public Film(String titolo,
                String regista,
                int annoUscita,
                Genere genere,
                int valutazionePersonale,
                StatoVisione statoVisione) {

        this.titolo = titolo;
        this.regista = regista;
        this.annoUscita = annoUscita;
        this.genere = genere;
        this.valutazionePersonale = valutazionePersonale;
        this.statoVisione = statoVisione;
    }


    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getRegista() {
        return regista;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public int getAnnoUscita() {
        return annoUscita;
    }

    public void setAnnoUscita(int annoUscita) {
        this.annoUscita = annoUscita;
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
        this.genere = genere;
    }

    public int getValutazionePersonale() {
        return valutazionePersonale;
    }

    public void setValutazionePersonale(int valutazionePersonale) {
        this.valutazionePersonale = valutazionePersonale;
    }

    public StatoVisione getStatoVisione() {
        return statoVisione;
    }

    public void setStatoVisione(StatoVisione statoVisione) {
        this.statoVisione = statoVisione;
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

