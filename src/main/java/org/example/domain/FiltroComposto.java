package org.example.domain;

import java.util.List;

public class FiltroComposto implements FiltraStrategy{

    // per combinare più filtri

    private final List<FiltraStrategy> filtri;

    public FiltroComposto(List<FiltraStrategy> filtri) {
        this.filtri = filtri;
    }

    @Override
    public boolean filtra(Film film) {
        for (FiltraStrategy f : filtri) {
            if (!f.filtra(film)) return false;
        }
        return true;
    }
}
