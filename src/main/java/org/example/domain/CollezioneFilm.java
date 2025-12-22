package org.example.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollezioneFilm {

    private final List<Film> films = new ArrayList<>();
    private final List<CollezioneFilmObserver> observers = new ArrayList<>();


    //ritorna false se nella collezione è già presente il film (quindi non lo aggiungiamo) true altr.
    public boolean aggiungiFilm(Film f) {
        if (films.contains(f)) return false;
        boolean aggiunto = films.add(f);
        if (aggiunto) notificaObservers();
        return aggiunto;
    }

    //rimuove film se presente, ritornando true se rimosso, false altr.

    public boolean rimuoviFilm(Film f) {
        boolean rimosso = films.remove(f);
        if (rimosso) notificaObservers();
        return rimosso;
    }


    public Film trova(String titolo, String regista) {
        Optional<Film> ret = films.stream()
                .filter(f -> f.getTitolo().equalsIgnoreCase(titolo)
                        && f.getRegista().equalsIgnoreCase(regista))
                .findFirst();

        return ret.orElse(null);
    }

    public List<Film> getTutti() {
        return List.copyOf(films);
    }

    public boolean aggiornaFilm(Film filmPrecedente, Film filmAggiornato) {
        int i = films.indexOf(filmPrecedente);
        if (i == -1) return false;
        films.set(i, filmAggiornato);
        notificaObservers();
        return true;
    }


    public List<Film> cercaPerTitolo(String titolo) {
        return films.stream().filter(f -> f.getTitolo().toLowerCase().contains(titolo.toLowerCase())).toList();
    }

    public List<Film> cercaPerRegista(String regista) {
        return films.stream().filter(f -> f.getRegista().toLowerCase().contains(regista.toLowerCase())).toList();
    }

    public List<Film> filtra(FiltraStrategy filtro) {
        return films.stream()
                .filter(filtro::filtra)
                .collect(Collectors.toList());
    }

    public List<Film> ordina(OrdinamentoStrategy ordinamento) {
        return films.stream()
                .sorted(ordinamento.ordina())
                .collect(Collectors.toList());
    }

    public void salvaSuRepository(FilmRepository repository) throws IOException {
        if (repository == null) {
            throw new IllegalArgumentException("Repository nulla");
        }
        // salvo uno snapshot della collezione (evito modifiche accidentali dall'esterno)
        repository.salva(List.copyOf(films));
    }

    public void caricaDaRepository(FilmRepository repository) throws IOException {
        if (repository == null) throw new IllegalArgumentException("Repository nulla");
        List<Film> caricati = repository.carica();
        films.clear();
        films.addAll(caricati);
        notificaObservers();
    }

    public void aggiungiObserver(CollezioneFilmObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void rimuoviObserver(CollezioneFilmObserver observer) {
        observers.remove(observer);
    }

    private void notificaObservers() {
        List<Film> snapshot = List.copyOf(films);
        for (CollezioneFilmObserver o : observers) {
            o.collezioneAggiornata(snapshot);
        }
    }
}
