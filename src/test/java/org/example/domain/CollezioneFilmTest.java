package org.example.domain;

import org.junit.Test;

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
                .titolo("Il Signore degli Anelli: La Compagnia dell'Anello")
                .regista("Peter Jackson")
                .annoUscita(2001)
                .genere(Genere.FANTASY)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film film2 = new FilmBuilder()
                .titolo("Il Signore degli Anelli: Le Due Torri")
                .regista("Peter Jackson")
                .annoUscita(2002)
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
    public void cercaPerRegista(){
        CollezioneFilm collezione = new CollezioneFilm();

        Film film1 = new FilmBuilder()
                .titolo("Pulp Fiction")
                .regista("Quentin Tarantino")
                .annoUscita(1994)
                .genere(Genere.DRAMMA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        Film film2 = new FilmBuilder()
                .titolo("Kill Bill")
                .regista("Quentin Tarantino")
                .annoUscita(2003)
                .genere(Genere.AZIONE)
                .valutazione(4)
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

}
