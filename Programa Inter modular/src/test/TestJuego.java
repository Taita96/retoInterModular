package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clases.Enemigo;
import clases.Juego;
import clases.Personaje;

public class TestJuego {
	static final int NUM_RONDAS = 3;
	static final int ULTIMA_RONDA = 1;
	
    Juego juego;

    @BeforeEach
    public void setUp() {
        juego = new Juego();
        juego.setnRondas(NUM_RONDAS);
        juego.nuevoGuerrero("Juan");
        juego.iniciarJuego();
    }

    @Test
    public void testInicioJuego() {
        assertNotNull(juego.getJugador(), "Existe un jugador");
        assertEquals(NUM_RONDAS, juego.getnRondas());
        assertNotNull(juego.getSiguiente(), "Debería haber un enemigo al comenzar el juego");
    }

    @Test
    public void testFinalJuegoSinEnemigos() {
        juego.eliminarEnemigos();
        assertTrue(juego.finalJuego(), "El juego debería finalizar cuando no hay enemigos");
    }

    @Test
    public void testTerminarRonda() {
        Enemigo enemigo = juego.getSiguiente();
        enemigo.setVida(0); // Simular que el enemigo ha muerto
        assertTrue(juego.terminarRonda(), "La ronda debería terminar si el enemigo está muerto");
        assertEquals(ULTIMA_RONDA, juego.getRonda());
    }

    @Test
    public void testJugadorEsGuerrero() {
        Personaje jugador = juego.getJugador();
        assertEquals("Juan", jugador.getNombre());
    }
}
