package clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Metodos {

	public static int aleatorio(int numMenor, int numMayor) {
		return (int) (Math.random() * (numMayor - numMenor + 1)) + numMenor;
	}

	public static int obtenerRecord(File archivo) {
		Scanner input = null;
		int record = 0;

		try {
			archivo = new File("mejorPuntuacion.txt");
			input = new Scanner(archivo);
			while (input.hasNextInt()) {
				record = input.nextInt();
			}
		} catch (FileNotFoundException e) {
			System.out.println("EL archivo no existe");
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return record;
	}

	public static void escribirRecord(File archivo, Juego juego, int record, String nombre) {
		if (juego.getnRondas() > record) {
			try {
				archivo = new File("mejorPuntuacion.txt");
				PrintWriter pw = new PrintWriter(archivo);
				pw.println(juego.getRonda());
				pw.println(nombre);
				pw.close();
			} catch (IOException e) {
				System.out.println("Ruta no existe");
			}
		}
	}
	
	public static void mostrarAnteriorRecor(File archivo){
		Scanner input = null;
		int record = obtenerRecord(archivo);
		String nombre = "";
		try {
			archivo = new File("mejorPuntuacion.txt");
			input = new Scanner(archivo);
			while (input.hasNext()) {
				nombre = input.next();
			}
		} catch (FileNotFoundException e) {
			System.out.println("EL archivo no existe");
		} finally {
			if (input != null) {
				input.close();
			}
		}
		
		System.out.println("\nEl jugador(a) " + nombre);
		System.out.println("Tiene un record Máximo de: " + record+"\n");
	}

	public static void elegirPersonaje(Scanner scanner, Juego juego, String nombre) {
		System.out.println("Elige tu personaje:");
		System.out.println("1. Mago");
		System.out.println("2. Guerrero");
		System.out.print("Elige (1, 2): ");
		int eleccionJugador = scanner.nextInt();

		scanner.nextLine();

		if (eleccionJugador == 1) {
			juego.nuevoMago(nombre);
		} else if (eleccionJugador == 2) {
			juego.nuevoGuerrero(nombre);
		} else {
			System.err.println("ERROR: Respuesta incorrecta, vuelve a intentarlo");
			System.out.println("================================================");
			elegirPersonaje(scanner, juego, nombre);
		}
		System.out.println("================================================");
	}

	public static void mostrarRondaActual(Juego juego) {
		System.out.printf("Ronda: %d/%d %n", juego.getRonda() + 1, juego.getnRondas());
	}

	public static void mostrarEnemigoActual(Enemigo enemigo) {
		System.out.printf("Estás luchando contra: %s (Vida: %d/%d)%n", enemigo.getNombre(), enemigo.getVida(),
				enemigo.getVidaInicial());
	}

	public static void mostrarJugador(Personaje jugador) {
		System.out.printf("Eres: %s %n", jugador, jugador.getVida(), jugador.getVidaInicial());
	}

	public static void elegirAccion(Scanner scanner, Juego juego, Personaje jugador, Enemigo enemigo) {
		System.out.println("Acciones:");
		System.out.println("1. Atacar");
		System.out.println("2. Curar");
		System.out.print("Elige (1, 2): ");
		int eleccionJugador = scanner.nextInt();

		scanner.nextLine();

		if (eleccionJugador == 1) {
			System.out.printf("%s ataca a %s%n", jugador.getNombre(), enemigo.getNombre());
			jugador.atacar(enemigo);
		} else if (eleccionJugador == 2) {
			System.out.printf("%s se cura%n", jugador.getNombre());
			if (jugador instanceof Guerrero) {
				((Guerrero) jugador).curar();
			} else if (jugador instanceof Mago) {
				((Mago) jugador).curar();
			}
		} else {
			System.err.println("ERROR: Respuesta incorrecta, vuelve a intentarlo");
			System.out.println("================================================");
			elegirAccion(scanner, juego, jugador, enemigo);
		}
	}

	public static void ataqueEnemigo(Personaje jugador, Enemigo enemigo) {
		System.out.printf("%s ataca a %s%n", enemigo.getNombre(), jugador.getNombre());
		enemigo.atacar(jugador);
	}

}
