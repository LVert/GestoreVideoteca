package org.example.domain;

public class FiltraPerValutazione implements FiltraStrategy {

    private final int valutazione;

    public FiltraPerValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    @Override
    public boolean filtra(Film film) {
        return film.getValutazionePersonale() >= valutazione;
    }
}
