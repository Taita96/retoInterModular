package clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Metodos {

	public static int aleatorio(int numMenor, int numMayor) {
		return (int) (Math.random() * (numMayor - numMenor + 1)) + numMenor;
	}

	public static int obtenerRecord(File archivo) {
		Scanner input = null;
		int record = 0;

		if (!archivo.exists()) {
			return 0;
		}

		try {
			input = new Scanner(archivo);
			while (input.hasNextInt()) {
				record = input.nextInt();
			}
		} catch (FileNotFoundException e) {
			System.err.println("El archivo no existe");
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return record;
	}

	public static void escribirRecord(File archivo, Juego juego, int record, String nombre) {
		if (juego.getnRondas() > record) {
			System.out.println("¡ES INCREIBLE " + nombre.toUpperCase() + "! HAS SUPERADO EL RECORD DE RONDAS.");
			System.out.println("================================================");
			try {
				PrintWriter pw = new PrintWriter(archivo);
				pw.println(juego.getRonda());
				pw.println(nombre);
				pw.close();
			} catch (IOException e) {
				System.err.println("Ruta no existe");
			}
		}
	}

	public static void mostrarAnteriorRecor(File archivo) {
		Scanner input = null;
		int record = obtenerRecord(archivo);
		String nombre = "";
		try {
			input = new Scanner(archivo);
			while (input.hasNext()) {
				nombre = input.next();
			}
		} catch (FileNotFoundException e) {
			System.err.println("El archivo no existe");
		} finally {
			if (input != null) {
				input.close();
			}
		}

		System.out.println("================================================");
		System.out.println("En el último juego: " + nombre);
		System.out.println("Tiene un record máximo de: " + record + " RONDAS");
		System.out.println("================================================");
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

	public static boolean elegirAccion(Scanner scanner, Juego juego, Personaje jugador, Enemigo enemigo) {
		System.out.println("Acciones:");
		System.out.println("1. Atacar");
		System.out.println("2. Curar");
		System.out.println("3. Guardar");
		System.out.print("Elige (1, 2, 3): ");
		int eleccionJugador = scanner.nextInt();

		scanner.nextLine();

		boolean dev = false;

		switch (eleccionJugador) {
		case 1:
			System.out.printf("%s ataca a %s%n", jugador.getNombre(), enemigo.getNombre());
			jugador.atacar(enemigo);
			ataqueEnemigo(jugador, enemigo);
			break;
		case 2:
			System.out.printf("%s se cura%n", jugador.getNombre());
			if (jugador instanceof Guerrero) {
				((Guerrero) jugador).curar();
			} else if (jugador instanceof Mago) {
				((Mago) jugador).curar();
			}
			ataqueEnemigo(jugador, enemigo);
			break;
		case 3:
			FileOutputStream fw = null;
			ObjectOutputStream salidaDatos = null;
			try {
				File guardarJuego = new File("datosAntJuego.dat");
				fw = new FileOutputStream(guardarJuego);
				salidaDatos = new ObjectOutputStream(fw);
				salidaDatos.writeObject(juego);
				dev = true;
				System.out.println("Partida guardada con ÉXITO");
			} catch (FileNotFoundException e) {
				System.out.println("Archivo no existe");
			} catch (IOException e) {
				System.out.println("El Documento no se pudo escribir correctamente");
			} finally {
				try {
					fw.close();
					salidaDatos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				juego.eliminarEnemigos();
			}
			break;
		default:
			System.err.println("ERROR: Respuesta incorrecta, vuelve a intentarlo");
			System.out.println("================================================");
			elegirAccion(scanner, juego, jugador, enemigo);

		}
		return dev;

	}

	public static void ataqueEnemigo(Personaje jugador, Enemigo enemigo) {
		System.out.printf("%s ataca a %s%n", enemigo.getNombre(), jugador.getNombre());
		enemigo.atacar(jugador);
	}

	public static int elegirOpc(Scanner scanner) {
		System.out.println("1. Nuevo Juego");
		System.out.println("2. Cargar partida anterior");
		System.out.print("Elige (1, 2): ");
		return scanner.nextInt();
	}

}
