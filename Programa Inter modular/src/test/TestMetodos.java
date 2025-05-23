package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import clases.Enemigo;
import clases.Guerrero;
import clases.Juego;
import clases.Metodos;
import clases.Personaje;

class TestMetodos {
	static final int ESCRIBIR_ARCHIVO = 325;
	static final int ELEGIR_RONDA = 5;
	static final int RONDA_MAX = 2;
	static final int NUM_VIDAS = 30;
	static final int VIDA_REDUCIDA = 30;
	static final int OPC_MAGO = 1;
	static final int OPC_GUERRERO = 2;
	static final int OPC_GUARDAR = 3;
	static final String OPC_CURAR = "2\n";
	static final String SCANNER_USUARIO_MAGO = "1\n";
	static final String SCANNER_USUARIO_GUERRERO = "2\n";
	static final String SCANNER_USUARIO_GUARDAR = "3\n";
	static final String NOMBRE = "Margarita";

	private File tempFile;

	@BeforeEach
	void setup() throws IOException {
		tempFile = File.createTempFile("testRecord", ".txt");
		tempFile.deleteOnExit();
	}

	@Test
	void testAleatorio() {
		for (int i = 0; i < 100; i++) {
			int result = Metodos.aleatorio(1, 10);
			assertTrue(result >= 1 && result <= 10);
		}
	}

	@Test
	void testObtenerRecord_archivoVacio() {
		assertEquals(0, Metodos.obtenerRecord(tempFile));
	}

	@Test
	void testObtenerRecord_conContenido() throws IOException {
		try (PrintWriter pw = new PrintWriter(tempFile)) {
			pw.println(ESCRIBIR_ARCHIVO);
		}
		assertEquals(ESCRIBIR_ARCHIVO, Metodos.obtenerRecord(tempFile));
	}

	@Test
	void testEscribirRecord_superaRecord() throws IOException {
		Juego juego = new Juego();
		juego.setnRondas(ELEGIR_RONDA);
		juego.setRonda(ELEGIR_RONDA);
		Metodos.escribirRecord(tempFile, juego, RONDA_MAX, NOMBRE);

		try (Scanner scanner = new Scanner(tempFile)) {
			assertEquals(String.valueOf(ELEGIR_RONDA), scanner.nextLine());
			assertEquals(NOMBRE, scanner.nextLine());
		}
	}

	@Test
	void testMostrarAnteriorRecor() throws IOException {
		PrintWriter pw = new PrintWriter(tempFile);

		pw.println(RONDA_MAX);
		pw.println(NOMBRE);
		pw.close();

		// Sin afirmación, solo comprueba que no se lanzan excepciones
		assertDoesNotThrow(() -> Metodos.mostrarAnteriorRecor(tempFile));
	}

	@Test
	void testElegirPersonaje_mago() {
		Scanner scanner = new Scanner(SCANNER_USUARIO_MAGO);
		Juego juego = new Juego();
		Metodos.elegirPersonaje(scanner, juego, NOMBRE);
		assertNotNull(juego.getJugador());
		assertEquals(NOMBRE, juego.getJugador().getNombre());
	}
	
	@Test
	void testElegirPersonaje_guerrero() {
		Scanner scanner = new Scanner(SCANNER_USUARIO_GUERRERO);
		Juego juego = new Juego();
		Metodos.elegirPersonaje(scanner, juego, NOMBRE);
		assertNotNull(juego.getJugador());
		assertEquals(NOMBRE, juego.getJugador().getNombre());
	}

	@Test
	void testMostrarRondaActual() {
		Juego juego = new Juego();
		juego.setnRondas(ELEGIR_RONDA);
		juego.setRonda(RONDA_MAX);
		assertDoesNotThrow(() -> Metodos.mostrarRondaActual(juego));
	}

	@Test
	void testMostrarEnemigoActual() {
		Enemigo enemigo = new Enemigo();
		assertDoesNotThrow(() -> Metodos.mostrarEnemigoActual(enemigo));
	}

	@Test
	void testMostrarJugador() {
		Personaje jugador = new Guerrero(NOMBRE, NUM_VIDAS);
		assertDoesNotThrow(() -> Metodos.mostrarJugador(jugador));
	}

	@Test
	void testElegirAccion_atacar() {
		Scanner scanner = new Scanner(SCANNER_USUARIO_MAGO);
		Juego juego = new Juego();
		Personaje jugador = new Guerrero(NOMBRE, NUM_VIDAS);
		Enemigo enemigo = new Enemigo();
		assertFalse(Metodos.elegirAccion(scanner, juego, jugador, enemigo));
	}

	@Test
	void testElegirAccion_guardar() {
		Scanner scanner = new Scanner(SCANNER_USUARIO_GUARDAR);
		Juego juego = new Juego();
		Personaje jugador = new Guerrero(NOMBRE, NUM_VIDAS);
		Enemigo enemigo = new Enemigo();
		assertTrue(Metodos.elegirAccion(scanner, juego, jugador, enemigo));
	}

	@Test
	void testAtaqueEnemigo() {
		Personaje jugador = new Guerrero(NOMBRE, NUM_VIDAS);
		Enemigo enemigo = new Enemigo();
		assertDoesNotThrow(() -> Metodos.ataqueEnemigo(jugador, enemigo));
	}

	@Test
	void testElegirOpc_mago() {
		Scanner scanner = new Scanner(SCANNER_USUARIO_MAGO);
		int opcion = Metodos.elegirOpc(scanner);
		assertEquals(OPC_MAGO, opcion);
	}
	
	@Test
	void testElegirOpc_guerrero() {
		Scanner scanner = new Scanner(SCANNER_USUARIO_GUERRERO);
		int opcion = Metodos.elegirOpc(scanner);
		assertEquals(OPC_GUERRERO, opcion);
	}
	
	@Test
	void testElegirOpc_guardar() {
		Scanner scanner = new Scanner(SCANNER_USUARIO_GUARDAR);
		int opcion = Metodos.elegirOpc(scanner);
		assertEquals(OPC_GUARDAR, opcion);
	}
	
	@Test
	public void testElegirAccion_GuerreroCura() {
	    Scanner scanner = new Scanner(OPC_CURAR);

	    Juego juego = new Juego();
	    Guerrero guerrero = new Guerrero(NOMBRE, NUM_VIDAS);
	    Enemigo enemigo = new Enemigo();

	    int vidaAntes = guerrero.getVida();
	    int pocionesAntes = guerrero.getPociones();

	    boolean resultado = Metodos.elegirAccion(scanner, juego, guerrero, enemigo);
	    
	    // El metodo atacar reduce la vida
	    assertEquals((pocionesAntes - 1), guerrero.getPociones(), "Debe haber usado una poción");
	    assertFalse(resultado, "No debería haberse guardado la partida");
	    assertEquals(vidaAntes, (guerrero.getVida() + 1)); 
	}
}
