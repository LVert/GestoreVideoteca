package org.example.gui;

import org.example.domain.Film;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class FilmTableModel extends AbstractTableModel {

    private final String[] columns = {"Titolo", "Regista", "Anno", "Genere", "Valutazione", "Stato"};
    private List<Film> data = new ArrayList<>();

    public void setFilms(List<Film> films) {
        this.data = new ArrayList<>(films);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Film f = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> f.getTitolo();
            case 1 -> f.getRegista();
            case 2 -> f.getAnnoUscita();
            case 3 -> f.getGenere();
            case 4 -> f.getValutazionePersonale();
            case 5 -> f.getStatoVisione();
            default -> "";
        };
    }

    public Film getFilmAt(int row) {
        if (row < 0 || row >= data.size()) return null;
        return data.get(row);
    }

    public List<Film> getFilms() {
        return new ArrayList<>(data);
    }
}
