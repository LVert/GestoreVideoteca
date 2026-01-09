package org.example.domain;

public class CercaPerRegista implements FiltraStrategy{

    private final String regista;

    public CercaPerRegista(String regista){
        this.regista = regista.toLowerCase();
    }


    @Override
    public boolean filtra(Film film) {
        return film.getRegista().toLowerCase().contains(regista);
    }
}
