package org.example.gui;

import org.example.domain.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideotecaFrame extends JFrame implements CollezioneFilmObserver {

    private final CollezioneFilm collezione;
    private final FilmTableModel tableModel = new FilmTableModel();
    private final JTable table = new JTable(tableModel);

    private OrdinamentoStrategy ordinamentoCorrente = new OrdinamentoPerTitolo();

    // campi input
    private final JTextField titoloField = new JTextField(10);
    private final JTextField registaField = new JTextField(10);
    private final JTextField annoField = new JTextField(4);

    private final JComboBox<Genere> genereBox = new JComboBox<>(Genere.values());
    private final JComboBox<Integer> valutazioneBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JComboBox<StatoVisione> statoBox = new JComboBox<>(StatoVisione.values());

    private final JTextField cercaField = new JTextField(12);
    private final JComboBox<String> tipoCercaBox = new JComboBox<>(new String[]{"Titolo", "Regista"});
    private final JButton cercaBtn = new JButton("Cerca");

    // ordinamento GUI
    private final JComboBox<String> sortBox = new JComboBox<>(new String[]{"Titolo", "Anno", "Valutazione"});

    private FiltraStrategy filtroCorrente = null;

    private final JCheckBox filtroGenereCheck = new JCheckBox("Genere");
    private final JComboBox<Genere> filtroGenereBox = new JComboBox<>(Genere.values());

    private final JCheckBox filtroStatoCheck = new JCheckBox("Stato");
    private final JComboBox<StatoVisione> filtroStatoBox = new JComboBox<>(StatoVisione.values());

    private final JCheckBox filtroValutazioneCheck = new JCheckBox("Valutazione ≥");
    private final JComboBox<Integer> filtroValutazioneBox = new JComboBox<>(new Integer[]{1,2,3,4,5});

    private final JComboBox<TipoPersistenza> formatoBox = new JComboBox<>(TipoPersistenza.values());

    private FilmRepository repositorySelezionata() {
        TipoPersistenza tipo = (TipoPersistenza) formatoBox.getSelectedItem();
        RepositoryFactory factory = RepositoryFactory.getFactory(tipo);
        return factory.creaFilmRepository();
    }

    public VideotecaFrame(CollezioneFilm collezione) {
        super("Gestione Videoteca");
        this.collezione = collezione;
        this.collezione.aggiungiObserver(this);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                int scelta = JOptionPane.showConfirmDialog(
                        VideotecaFrame.this,
                        "Vuoi salvare la collezione prima di uscire?",
                        "Uscita",
                        JOptionPane.YES_NO_CANCEL_OPTION
                );

                if (scelta == JOptionPane.CANCEL_OPTION || scelta == JOptionPane.CLOSED_OPTION) {
                    return; // annulla chiusura
                }

                if (scelta == JOptionPane.YES_OPTION) {
                    try {
                        // 1. Identifica il tipo selezionato e il repository
                        TipoPersistenza tipoScelto = (TipoPersistenza) formatoBox.getSelectedItem();
                        FilmRepository repo = repositorySelezionata();

                        // 2. Salva la collezione nel formato attuale
                        collezione.salvaSuRepository(repo);

                        // 3. LOGICA DI MIGRAZIONE: Elimina il file del formato non utilizzato
                        if (tipoScelto == TipoPersistenza.JSON) {
                            java.nio.file.Files.deleteIfExists(java.nio.file.Path.of("videoteca.csv"));
                        } else if (tipoScelto == TipoPersistenza.CSV) {
                            java.nio.file.Files.deleteIfExists(java.nio.file.Path.of("videoteca.json"));
                        }

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                VideotecaFrame.this,
                                "Errore durante il salvataggio o la migrazione: " + ex.getMessage(),
                                "Errore",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return; // Non chiudere l'app se il salvataggio fallisce
                    }
                }

                // NO oppure YES riuscito -> chiudi davvero
                dispose();
                System.exit(0);
            }
        });
        setSize(1100, 520);
        setLocationRelativeTo(null);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(buildTopPanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildButtonsPanel(), BorderLayout.SOUTH);

        // stato iniziale (ordino secondo ordinamento corrente)
        collezioneAggiornata(this.collezione.getTutti());
    }

    private JPanel buildTopPanel(){
        JPanel pannelloTop = new JPanel(new BorderLayout());

        // pannello aggiunta
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPanel.add(new JLabel("Titolo:")); addPanel.add(titoloField);
        addPanel.add(new JLabel("Regista:")); addPanel.add(registaField);
        addPanel.add(new JLabel("Anno:")); addPanel.add(annoField);
        addPanel.add(new JLabel("Genere:")); addPanel.add(genereBox);
        addPanel.add(new JLabel("Val:")); addPanel.add(valutazioneBox);
        addPanel.add(new JLabel("Stato:")); addPanel.add(statoBox);

        // Pannello Ricerca
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Cerca"));
        searchPanel.add(tipoCercaBox);
        searchPanel.add(cercaField);
        searchPanel.add(cercaBtn);

        pannelloTop.add(addPanel, BorderLayout.WEST);
        pannelloTop.add(searchPanel, BorderLayout.EAST);

        return pannelloTop;
    }



    private JPanel buildButtonsPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JButton addBtn = new JButton("Aggiungi");
        JButton editBtn = new JButton("Modifica selezionato");
        JButton removeBtn = new JButton("Rimuovi selezionato");

        JButton loadBtn = new JButton("Carica");
        JButton saveBtn = new JButton("Salva");

        JButton applySortBtn = new JButton("Applica ordinamento");

        JButton applyFilterBtn = new JButton("Applica filtri");
        JButton resetFilterBtn = new JButton("Reset filtri");

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        row1.add(new JLabel("Formato:"));
        row1.add(formatoBox);
        row1.add(loadBtn);
        row1.add(saveBtn);

        row1.add(removeBtn);
        row1.add(editBtn);
        row1.add(addBtn);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        row2.add(new JLabel("Ordina per:"));
        row2.add(sortBox);
        row2.add(applySortBtn);

        row2.add(Box.createHorizontalStrut(20)); // spazio visivo

        row2.add(new JLabel("Filtri:"));
        row2.add(filtroGenereCheck);
        row2.add(filtroGenereBox);
        row2.add(filtroStatoCheck);
        row2.add(filtroStatoBox);
        row2.add(filtroValutazioneCheck);
        row2.add(filtroValutazioneBox);
        row2.add(applyFilterBtn);
        row2.add(resetFilterBtn);

        //aggiunta
        addBtn.addActionListener(e -> {
            try {
                Film film = new FilmBuilder()
                        .titolo(titoloField.getText())
                        .regista(registaField.getText())
                        .annoUscita(Integer.parseInt(annoField.getText()))
                        .genere((Genere) genereBox.getSelectedItem())
                        .valutazione((Integer) valutazioneBox.getSelectedItem())
                        .stato((StatoVisione) statoBox.getSelectedItem())
                        .build();

                boolean ok = collezione.aggiungiFilm(film);
                if (!ok) {
                    JOptionPane.showMessageDialog(this, "Film già presente (titolo + regista).");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        });

        //rimozione
        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un film dalla tabella.");
                return;
            }

            Film selected = tableModel.getFilmAt(row);
            if (selected == null) return;

            int scelta = JOptionPane.showConfirmDialog(
                    this,
                    "Vuoi rimuovere: " + selected.getTitolo() + " (" + selected.getRegista() + ")?",
                    "Conferma rimozione",
                    JOptionPane.YES_NO_OPTION
            );

            if (scelta == JOptionPane.YES_OPTION) {
                collezione.rimuoviFilm(selected);
            }
        });

        //modifica
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un film dalla tabella.");
                return;
            }

            Film precedente = tableModel.getFilmAt(row);
            if (precedente == null) return;

            // precompilo
            JTextField titolo = new JTextField(precedente.getTitolo(), 20);
            JTextField regista = new JTextField(precedente.getRegista(), 20);
            JTextField anno = new JTextField(String.valueOf(precedente.getAnnoUscita()), 6);

            JComboBox<Genere> genere = new JComboBox<>(Genere.values());
            genere.setSelectedItem(precedente.getGenere());

            JComboBox<Integer> valutazione = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
            valutazione.setSelectedItem(precedente.getValutazionePersonale());

            JComboBox<StatoVisione> stato = new JComboBox<>(StatoVisione.values());
            stato.setSelectedItem(precedente.getStatoVisione());

            JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
            panel.add(new JLabel("Titolo:")); panel.add(titolo);
            panel.add(new JLabel("Regista:")); panel.add(regista);
            panel.add(new JLabel("Anno:")); panel.add(anno);
            panel.add(new JLabel("Genere:")); panel.add(genere);
            panel.add(new JLabel("Valutazione:")); panel.add(valutazione);
            panel.add(new JLabel("Stato:")); panel.add(stato);

            int result = JOptionPane.showConfirmDialog(
                    this, panel, "Modifica Film", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    Film aggiornato = new FilmBuilder()
                            .titolo(titolo.getText())
                            .regista(regista.getText())
                            .annoUscita(Integer.parseInt(anno.getText()))
                            .genere((Genere) genere.getSelectedItem())
                            .valutazione((Integer) valutazione.getSelectedItem())
                            .stato((StatoVisione) stato.getSelectedItem())
                            .build();

                    boolean ok = collezione.aggiornaFilm(precedente, aggiornato);
                    if (!ok) {
                        JOptionPane.showMessageDialog(this, "Aggiornamento fallito: film non trovato.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
                }
            }
        });

        // carica/salva
        loadBtn.addActionListener(e -> {
            try {
                FilmRepository repo = repositorySelezionata();
                collezione.caricaDaRepository(repo);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Errore caricamento: " + ex.getMessage());
            }
        });

        saveBtn.addActionListener(e -> {
            try {
                 //Identifico il tipo selezionato nella JComboBox
                TipoPersistenza tipoScelto = (TipoPersistenza) formatoBox.getSelectedItem();
                FilmRepository repo = repositorySelezionata();

                //Salvo nel formato attuale (copia i dati dalla RAM al file)
                collezione.salvaSuRepository(repo);

                //Logica di Migrazione: svuoto/elimino l'altro formato
                if (tipoScelto == TipoPersistenza.JSON) {
                    java.nio.file.Files.deleteIfExists(java.nio.file.Path.of("videoteca.csv"));
                    System.out.println("Salvataggio JSON completato. File CSV rimosso.");
                } else {
                    java.nio.file.Files.deleteIfExists(java.nio.file.Path.of("videoteca.json"));
                    System.out.println("Salvataggio CSV completato. File JSON rimosso.");
                }


            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Errore durante il salvataggio: " + ex.getMessage());
            }
        });


        //ordinamento
        applySortBtn.addActionListener(e -> {
            String scelta = (String) sortBox.getSelectedItem();
            if ("Anno".equals(scelta)) ordinamentoCorrente = new OrdinamentoPerAnno();
            else if ("Valutazione".equals(scelta)) ordinamentoCorrente = new OrdinamentoPerValutazione();
            else ordinamentoCorrente = new OrdinamentoPerTitolo();

            // riapplico ordinamento subito
            collezioneAggiornata(collezione.getTutti());
        });

        //filtri e ricerca

        cercaBtn.addActionListener(e -> applyFilterBtn.doClick());

        applyFilterBtn.addActionListener(e -> {
            List<FiltraStrategy> filtri = new ArrayList<>();

            //Aggiungo ricerca testuale
            String film = cercaField.getText().trim();
            if (!film.isEmpty()) {
                if (tipoCercaBox.getSelectedItem().equals("Titolo")) {
                    filtri.add(new CercaPerTitolo(film));
                } else {
                    filtri.add(new CercaPerRegista(film));
                }
            }

            //Aggiungo filtri checkbox
            if (filtroGenereCheck.isSelected()) {
                filtri.add(new FiltroPerGenere((Genere) filtroGenereBox.getSelectedItem()));
            }
            if (filtroStatoCheck.isSelected()) {
                filtri.add(new FiltraPerStatoVisione((StatoVisione) filtroStatoBox.getSelectedItem()));
            }
            if (filtroValutazioneCheck.isSelected()) {
                filtri.add(new FiltraPerValutazione((Integer) filtroValutazioneBox.getSelectedItem()));
            }

            // Applico il FiltroComposto
            if (filtri.isEmpty()) {
                filtroCorrente = null;
            } else if (filtri.size() == 1) {
                filtroCorrente = filtri.get(0);
            } else {
                filtroCorrente = new FiltroComposto(filtri);
            }

            collezioneAggiornata(collezione.getTutti());
        });

        // Reset
        resetFilterBtn.addActionListener(e -> {
            filtroCorrente = null;
            cercaField.setText("");
            filtroGenereCheck.setSelected(false);
            filtroStatoCheck.setSelected(false);
            filtroValutazioneCheck.setSelected(false);
            collezioneAggiornata(collezione.getTutti());
        });

        p.add(row2);
        p.add(row1);

        return p;
    }

    @Override
    public void collezioneAggiornata(List<Film> films) {
        SwingUtilities.invokeLater(() -> {

            List<Film> visible = new ArrayList<>();
            if (filtroCorrente == null) {
                visible.addAll(films);
            } else {
                for (Film f : films) {
                    if (filtroCorrente.filtra(f)) {
                        visible.add(f);
                    }
                }
            }

            if (ordinamentoCorrente != null) {
                visible.sort(ordinamentoCorrente.ordina());
            }

            // refresh tabella
            tableModel.setFilms(visible);
        });

    }




}
