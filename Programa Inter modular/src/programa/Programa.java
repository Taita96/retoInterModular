package programa;

import java.io.File;
import java.util.Scanner;

import clases.Enemigo;
import clases.Juego;
import clases.Metodos;
import clases.Personaje;

public class Programa {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean jugarDeNuevo;
		File archivo = null;
		do {
			Juego juego = new Juego();

			System.out.println("BIENVENID@ AL JUEGO:");
			
			Metodos.mostrarAnteriorRecor(archivo);
			
			System.out.print("¿Cuántas rondas quieres jugar? ");
			int rondasJugador = scanner.nextInt();

			scanner.nextLine();

			juego.setnRondas(rondasJugador);

			System.out.print("Introduce tu nombre: ");
			String nombreJugador = scanner.nextLine();
			
			int record = Metodos.obtenerRecord(archivo);

			Metodos.elegirPersonaje(scanner, juego, nombreJugador);

			juego.iniciarJuego();

			boolean jugadorDerrotado = false;
			
			do {
				Personaje jugador = juego.getJugador();
				Enemigo enemigo = juego.getSiguiente();

				while (!enemigo.muerto() && !jugador.muerto()) {
					Metodos.mostrarRondaActual(juego);
					Metodos.mostrarEnemigoActual(enemigo);
					Metodos.mostrarJugador(jugador);
					Metodos.elegirAccion(scanner, juego, jugador, enemigo);

					if (!enemigo.muerto()) {
						Metodos.ataqueEnemigo(jugador, enemigo);
					}

					System.out.println("================================================");
				}

				if (juego.terminarRonda()) {
					System.out.printf("¡%s HA SIDO VENCIDO!%n", enemigo.getNombre().toUpperCase());
					System.out.println("================================================");
				}

				if (jugador.muerto()) {
					System.out.printf("¡LO SIENTO %s, HAS SIDO DERROTADO!%n", nombreJugador.toUpperCase());
					System.out.println("================================================");
					jugadorDerrotado = true;
					break;
				}
			} while (!juego.finalJuego());

			if (!jugadorDerrotado) {
				System.out.printf("¡FELICIDADES %s! HAS GANADO EL JUEGO.%n", nombreJugador.toUpperCase());
				System.out.println("================================================");
				Metodos.escribirRecord(archivo, juego, record, nombreJugador);
			}
		
			
			
			
			System.out.print("¿Quieres volver a jugar? (si/no): "); 
			String respuesta = scanner.nextLine().toLowerCase();
			jugarDeNuevo = respuesta.startsWith("s");
			System.out.println("================================================");

		} while (jugarDeNuevo);

		System.out.println("¡GRACIAS POR JUGAR!");
		System.out.println("...FIN DEL JUEGO");
		System.out.println("================================================");
		scanner.close();
	}

}