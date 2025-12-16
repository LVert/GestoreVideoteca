package org.example.domain;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;


import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.*;


public class CollezioneFilmTest {

    @Test
    public void aggiungiFilmTest(){
        CollezioneFilm collezione = new CollezioneFilm();

        Film f = new FilmBuilder()
                .titolo("L'attimo fuggente")
                .regista("Peter Weir")
                .annoUscita(1989)
                .genere(Genere.COMMEDIA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        boolean aggiunto = collezione.aggiungiFilm(f);
        assertTrue(aggiunto);
        assertEquals(1,collezione.getTutti().size());
        assertTrue(collezione.getTutti().contains(f));
    }

    @Test
    public void rimuoviFilmTest(){
        CollezioneFilm collezione = new CollezioneFilm();
        Film f1 = new FilmBuilder()
                .titolo("L'attimo fuggente")
                .regista(("Peter Weir"))
                .annoUscita(1989)
                .genere(Genere.COMMEDIA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();
        Film f2 = new FilmBuilder()
                .titolo("Bastardi senza gloria")
                .regista("Quentin Tarantino")
                .annoUscita(2009)
                .genere(Genere.AZIONE)
                .valutazione(4)
                .stato(StatoVisione.VISTO)
                .build();

        collezione.aggiungiFilm(f1);
        collezione.aggiungiFilm(f2);
        assertEquals(2,collezione.getTutti().size());
        collezione.rimuoviFilm(f1);
        assertEquals(1,collezione.getTutti().size());
        assertTrue(collezione.getTutti().contains(f2));

    }

    @Test
    public void trovaTest(){
        CollezioneFilm collezione = new CollezioneFilm();

        Film f1 = new FilmBuilder()
                .titolo("L'attimo fuggente")
                .regista("Peter Weir")
                .annoUscita(1989)
                .genere(Genere.COMMEDIA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film f2 = new FilmBuilder()
                .titolo("Bastardi senza gloria")
                .regista("Quentin Tarantino")
                .annoUscita(2009)
                .genere(Genere.AZIONE)
                .valutazione(4)
                .stato(StatoVisione.VISTO)
                .build();

        collezione.aggiungiFilm(f1);
        collezione.aggiungiFilm(f2);

        assertEquals(2,collezione.getTutti().size());

        Film trovato = collezione.trova("L'attimo fuggente","Peter Weir");

        assertNotNull(trovato);  //accerto che il film sia stato trovato

    }

    @Test
    public void aggiornaFilmTest(){
        CollezioneFilm collezione = new CollezioneFilm();

        Film precedente = new FilmBuilder()
                .titolo("Matrix")
                .regista("The Wachowskis")
                .annoUscita(1999)
                .genere(Genere.AZIONE)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        collezione.aggiungiFilm(precedente);

        Film aggiornato  = new FilmBuilder()
                .titolo("Bastardi senza gloria")
                .regista("Quentin Tarantino")
                .annoUscita(2009)
                .genere(Genere.AZIONE)
                .valutazione(4)
                .stato(StatoVisione.VISTO)
                .build();

        boolean esito = collezione.aggiornaFilm(precedente, aggiornato);

        //verifico che ci sia sempre un solo film
        assertTrue(esito);
        assertEquals(1,collezione.getTutti().size());

        //verifico che il film attuale nella collezione sia quello che volevamo
        Film film = collezione.getTutti().get(0);
        assertEquals(4, film.getValutazionePersonale());
        assertEquals("Bastardi senza gloria", film.getTitolo());
        assertEquals("Quentin Tarantino", film.getRegista());

    }

    @Test
    public void cercaPerTitoloTest(){

        CollezioneFilm collezione = new CollezioneFilm();

        Film film1 = new FilmBuilder()
                .titolo("Il Signore degli Anelli: Le Due Torri")
                .regista("Peter Jackson")
                .annoUscita(2002)
                .genere(Genere.FANTASY)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film film2 = new FilmBuilder()
                .titolo("Il Signore degli Anelli: La Compagnia dell'Anello")
                .regista("Peter Jackson")
                .annoUscita(2001)
                .genere(Genere.FANTASY)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film film3 = new FilmBuilder()
                .titolo("Harry Potter e la Pietra Filosofale")
                .regista("Chris Columbus")
                .annoUscita(2001)
                .genere(Genere.FANTASY)
                .valutazione(4)
                .stato(StatoVisione.VISTO)
                .build();

        collezione.aggiungiFilm(film1);
        collezione.aggiungiFilm(film2);
        collezione.aggiungiFilm(film3);

        List<Film> trovati = collezione.cercaPerTitolo("signore degli anelli");

        assertEquals(2, trovati.size());
        assertTrue(trovati.contains(film1));
        assertTrue(trovati.contains(film2));
        assertFalse(trovati.contains(film3));

    }

    @Test
    public void cercaPerRegistaTest(){
        CollezioneFilm collezione = new CollezioneFilm();

        Film film1 = new FilmBuilder()
                .titolo("Kill Bill")
                .regista("Quentin Tarantino")
                .annoUscita(2003)
                .genere(Genere.AZIONE)
                .valutazione(4)
                .stato(StatoVisione.VISTO)
                .build();

        Film film2 = new FilmBuilder()
                .titolo("Pulp Fiction")
                .regista("Quentin Tarantino")
                .annoUscita(1994)
                .genere(Genere.DRAMMA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film film3 = new FilmBuilder()
                .titolo("The Departed")
                .regista("Martin Scorsese")
                .annoUscita(2006)
                .genere(Genere.DRAMMA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        collezione.aggiungiFilm(film1);
        collezione.aggiungiFilm(film2);
        collezione.aggiungiFilm(film3);

        List<Film> trovati = collezione.cercaPerRegista("tarantino");

        assertEquals(2, trovati.size());
        assertTrue(trovati.contains(film1));
        assertTrue(trovati.contains(film2));
        assertFalse(trovati.contains(film3));

    }

    @Test
    public void ordinaPerTitoloTest(){

        CollezioneFilm collezione = new CollezioneFilm();

        Film film1 = new FilmBuilder()
                .titolo("Il Signore degli Anelli: Le Due Torri")
                .regista("Peter Jackson")
                .annoUscita(2002)
                .genere(Genere.FANTASY)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film film2 = new FilmBuilder()
                .titolo("Il Signore degli Anelli: La Compagnia dell'Anello")
                .regista("Peter Jackson")
                .annoUscita(2001)
                .genere(Genere.FANTASY)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film film3 = new FilmBuilder()
                .titolo("Harry Potter e la Pietra Filosofale")
                .regista("Chris Columbus")
                .annoUscita(2001)
                .genere(Genere.FANTASY)
                .valutazione(4)
                .stato(StatoVisione.VISTO)
                .build();

        collezione.aggiungiFilm(film1);
        collezione.aggiungiFilm(film2);
        collezione.aggiungiFilm(film3);

        OrdinamentoStrategy ordinaPerTitolo = new OrdinamentoPerTitolo();
        List<Film> trovati = collezione.ordina(ordinaPerTitolo);

        assertEquals(trovati.get(0), film3);
        assertEquals(trovati.get(1), film2);
        assertEquals(trovati.get(2), film1);

    }

    @Test
    public void ordinaPerAnnoTest(){
        CollezioneFilm collezione = new CollezioneFilm();

        Film film1 = new FilmBuilder()
                .titolo("Kill Bill")
                .regista("Quentin Tarantino")
                .annoUscita(2003)
                .genere(Genere.AZIONE)
                .valutazione(4)
                .stato(StatoVisione.VISTO)
                .build();

        Film film2 = new FilmBuilder()
                .titolo("Pulp Fiction")
                .regista("Quentin Tarantino")
                .annoUscita(1994)
                .genere(Genere.DRAMMA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film film3 = new FilmBuilder()
                .titolo("The Departed")
                .regista("Martin Scorsese")
                .annoUscita(2006)
                .genere(Genere.DRAMMA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        collezione.aggiungiFilm(film1);
        collezione.aggiungiFilm(film2);
        collezione.aggiungiFilm(film3);

        OrdinamentoStrategy ordinaPerAnno =  new OrdinamentoPerAnno();
        List<Film> trovati = collezione.ordina(ordinaPerAnno);

        assertEquals(trovati.get(0), film2);
        assertEquals(trovati.get(1), film1);
        assertEquals(trovati.get(2), film3);
    }

    @Test
    public void filtraPerGenereTest(){
        CollezioneFilm collezione = new CollezioneFilm();

        Film film1 = new FilmBuilder()
                .titolo("Il Signore degli Anelli: La Compagnia dell'Anello")
                .regista("Peter Jackson")
                .annoUscita(2001)
                .genere(Genere.FANTASY)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film film2 = new FilmBuilder()
                .titolo("The Matrix")
                .regista("The Wachowskis")
                .annoUscita(1999)
                .genere(Genere.AZIONE)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film film3 = new FilmBuilder()
                .titolo("Pulp Fiction")
                .regista("Quentin Tarantino")
                .annoUscita(1994)
                .genere(Genere.DRAMMA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        collezione.aggiungiFilm(film1);
        collezione.aggiungiFilm(film2);
        collezione.aggiungiFilm(film3);

        FiltraStrategy filtroPerGenere = new FiltroPerGenere(Genere.FANTASY);
        List<Film> filmsFiltrati = collezione.filtra(filtroPerGenere);

        assertEquals(1, filmsFiltrati.size());
        assertTrue(filmsFiltrati.contains(film1));
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void salvaECaricaDaRepositoryJsonTest() throws IOException {

        // repo su file temporaneo
        File jsonFile = tempFolder.newFile("videoteca_test.json");
        FilmRepository repo = new JsonFilmRepository(jsonFile.getAbsolutePath());

        // collezione 1 -> salva
        CollezioneFilm collezione1 = new CollezioneFilm();

        Film f1 = new FilmBuilder()
                .titolo("Matrix")
                .regista("The Wachowskis")
                .annoUscita(1999)
                .genere(Genere.AZIONE)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film f2 = new FilmBuilder()
                .titolo("Inception")
                .regista("Christopher Nolan")
                .annoUscita(2010)
                .genere(Genere.THRILLER)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        collezione1.aggiungiFilm(f1);
        collezione1.aggiungiFilm(f2);

        collezione1.salvaSuRepository(repo);

        // collezione 2 -> carica
        CollezioneFilm collezione2 = new CollezioneFilm();
        collezione2.caricaDaRepository(repo);

        assertEquals(2, collezione2.getTutti().size());
        assertTrue(collezione2.getTutti().contains(f1));
        assertTrue(collezione2.getTutti().contains(f2));
    }

    @Test
    public void caricaDaRepositoryJsvESostituisceContenutoTest() throws IOException {

        File jsonFile = tempFolder.newFile("videoteca_replace.json");
        FilmRepository repo = new JsonFilmRepository(jsonFile.getAbsolutePath());

        Film salvato = new FilmBuilder()
                .titolo("Pulp Fiction")
                .regista("Quentin Tarantino")
                .annoUscita(1994)
                .genere(Genere.DRAMMA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        // preparo un file JSON con 1 film
        CollezioneFilm collezioneSalvataggio = new CollezioneFilm();
        collezioneSalvataggio.aggiungiFilm(salvato);
        collezioneSalvataggio.salvaSuRepository(repo);

        // collezione target parte con un altro film
        CollezioneFilm collezioneTarget = new CollezioneFilm();
        Film diverso = new FilmBuilder()
                .titolo("Film Diverso")
                .regista("Regista Diverso")
                .annoUscita(2000)
                .genere(Genere.ALTRO)
                .valutazione(3)
                .stato(StatoVisione.DA_VEDERE)
                .build();
        collezioneTarget.aggiungiFilm(diverso);

        assertEquals(1, collezioneTarget.getTutti().size());
        assertTrue(collezioneTarget.getTutti().contains(diverso));

        // ora carico dal repo: deve rimpiazzare
        collezioneTarget.caricaDaRepository(repo);

        assertEquals(1, collezioneTarget.getTutti().size());
        assertTrue(collezioneTarget.getTutti().contains(salvato));
        assertFalse(collezioneTarget.getTutti().contains(diverso));
    }

    @Test
    public void salvaECaricaDaRepositoryCsvTest() throws IOException {

        File csvFile = tempFolder.newFile("videoteca_test.csv");
        FilmRepository repo = new CsvFilmRepository(csvFile.getAbsolutePath());

        CollezioneFilm collezione1 = new CollezioneFilm();

        Film f1 = new FilmBuilder()
                .titolo("Matrix")
                .regista("The Wachowskis")
                .annoUscita(1999)
                .genere(Genere.AZIONE)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film f2 = new FilmBuilder()
                .titolo("Inception")
                .regista("Christopher Nolan")
                .annoUscita(2010)
                .genere(Genere.THRILLER)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        collezione1.aggiungiFilm(f1);
        collezione1.aggiungiFilm(f2);

        collezione1.salvaSuRepository(repo);

        CollezioneFilm collezione2 = new CollezioneFilm();
        collezione2.caricaDaRepository(repo);

        assertEquals(2, collezione2.getTutti().size());
        assertTrue(collezione2.getTutti().contains(f1));
        assertTrue(collezione2.getTutti().contains(f2));
    }

    @Test
    public void caricaDaRepositoryCsvESostituisceContenutoTest() throws IOException {

        File csvFile = tempFolder.newFile("videoteca_replace.csv");
        FilmRepository repo = new CsvFilmRepository(csvFile.getAbsolutePath());

        Film salvato = new FilmBuilder()
                .titolo("Pulp Fiction")
                .regista("Quentin Tarantino")
                .annoUscita(1994)
                .genere(Genere.DRAMMA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        // creo il file con 1 film
        CollezioneFilm collezioneSalvataggio = new CollezioneFilm();
        collezioneSalvataggio.aggiungiFilm(salvato);
        collezioneSalvataggio.salvaSuRepository(repo);

        // collezione target parte con un altro film
        CollezioneFilm collezioneTarget = new CollezioneFilm();
        Film diverso = new FilmBuilder()
                .titolo("Film Diverso")
                .regista("Regista Diverso")
                .annoUscita(2000)
                .genere(Genere.ALTRO)
                .valutazione(3)
                .stato(StatoVisione.DA_VEDERE)
                .build();
        collezioneTarget.aggiungiFilm(diverso);

        assertEquals(1, collezioneTarget.getTutti().size());
        assertTrue(collezioneTarget.getTutti().contains(diverso));

        // carico dal repo: deve rimpiazzare
        collezioneTarget.caricaDaRepository(repo);

        assertEquals(1, collezioneTarget.getTutti().size());
        assertTrue(collezioneTarget.getTutti().contains(salvato));
        assertFalse(collezioneTarget.getTutti().contains(diverso));
    }

}
