package org.example.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollezioneFilm {

    private final List<Film> films = new ArrayList<>();

    //ritorna false se nella collezione è già presente il film (quindi non lo aggiungiamo) true altr.
    public boolean aggiungiFilm(Film f){
        if(this.films.contains(f))
            return false;
        films.add(f);
        return true;
    }

    //rimuove film se presente, ritornando true se rimosso, false altr.

    public boolean rimuoviFilm(Film f){
        return films.remove(f);
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

    public boolean aggiornaFilm(Film filmPrecedente, Film filmAggiornato){
        int ind = films.indexOf(filmPrecedente);
        if(ind == -1) return false;

        films.set(ind,filmAggiornato);
        return true;
    }


    public List<Film> cercaPerTitolo(String titolo){
        return films.stream().filter(f -> f.getTitolo().toLowerCase().contains(titolo.toLowerCase())).toList();
    }

    public List<Film> cercaPerRegista(String regista){
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
        if (repository == null) {
            throw new IllegalArgumentException("Repository nulla");
        }
        List<Film> caricati = repository.carica();
        films.clear();
        films.addAll(caricati);
    }
}
