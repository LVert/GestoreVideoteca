package org.example.domain;

import org.junit.Test;
import static org.junit.Assert.*;


public class FilmBuilderTest {

    @Test
    public void creaFilmTest() {
        Film film = new FilmBuilder()
                .titolo("Will hunting - Genio Ribelle")
                .regista("Gus Van Sant")
                .annoUscita(1997)
                .genere(Genere.DRAMMA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build();

        assertEquals("Will hunting - Genio Ribelle", film.getTitolo());
        assertEquals("Gus Van Sant", film.getRegista());
        assertEquals(1997, film.getAnnoUscita());
        assertEquals(Genere.DRAMMA, film.getGenere());
        assertEquals(5, film.getValutazionePersonale());
        assertEquals(StatoVisione.VISTO, film.getStatoVisione());
    }

    @Test
    public void testAnnoUscita() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new FilmBuilder()
                .titolo("Will hunting - Genio Ribelle")
                .regista("Gus Van Sant")
                .annoUscita(1800)
                .genere(Genere.DRAMMA)
                .valutazione(5)
                .stato(StatoVisione.VISTO)
                .build()
        );
        assertEquals("Anno di uscita non valido.", exception.getMessage());
    }

}
