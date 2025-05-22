package programa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import clases.Enemigo;
import clases.Juego;
import clases.Metodos;
import clases.Personaje;

public class Programa {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean jugarDeNuevo = false;
		File archivo = new File("mejorPuntuacion.txt");
		File cargarPartida = new File("datosAntJuego.dat");
		Juego juego = null;
		
		do {
			int record = Metodos.obtenerRecord(archivo);
			System.out.println("BIENVENID@ AL JUEGO:");

			Metodos.mostrarAnteriorRecor(archivo);

			int opcUser = Metodos.elegirOpc(scanner);

			switch (opcUser) {
			case 1:
				juego = new Juego();
				System.out.print("¿Cuántas rondas quieres jugar? ");
				int rondasJugador = scanner.nextInt();

				scanner.nextLine();

				juego.setnRondas(rondasJugador);

				System.out.print("Introduce tu nombre: ");
				String nombreJugador = scanner.nextLine();

				Metodos.elegirPersonaje(scanner, juego, nombreJugador);
				juego.iniciarJuego();
				break;

			case 2:
				FileInputStream fs = null;
				ObjectInputStream os = null;
				try {
					fs = new FileInputStream(cargarPartida);
					os = new ObjectInputStream(fs);
					Object o = os.readObject();
					juego = ((Juego)o);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					System.err.println("Archivo no existe");
				} catch (IOException e) {
					System.err.println("Error al leer el archivo");
				} finally {
					try {
						fs.close();
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}

				break;

			default:
				System.err.println("Respuesta Incorrecta");
			}

			boolean jugadorDerrotado = false;
			boolean partidaGuardada = false;

			do {
				Personaje jugador = juego.getJugador();
				Enemigo enemigo = juego.getSiguiente();

				while (!enemigo.muerto() && !jugador.muerto() && !juego.finalJuego()) {
					Metodos.mostrarRondaActual(juego);
					Metodos.mostrarEnemigoActual(enemigo);
					Metodos.mostrarJugador(jugador);
					partidaGuardada = Metodos.elegirAccion(scanner, juego, jugador, enemigo);

					System.out.println("================================================");
				}

				if (juego.terminarRonda()) {
					System.out.printf("¡%s HA SIDO VENCIDO!%n", enemigo.getNombre().toUpperCase());
					System.out.println("================================================");
				}

				if (jugador.muerto()) {
					System.out.printf("¡LO SIENTO %s, HAS SIDO DERROTADO!%n",
							juego.getJugador().getNombre().toUpperCase());
					System.out.println("================================================");
					jugadorDerrotado = true;
					break;
				}

			} while (!juego.finalJuego());
			
			if (partidaGuardada) {
				jugarDeNuevo = false;
			} else {
				if (!jugadorDerrotado) {
					System.out.printf("¡FELICIDADES %s! HAS GANADO EL JUEGO.%n",
							juego.getJugador().getNombre().toUpperCase());
					System.out.println("================================================");
					Metodos.escribirRecord(archivo, juego, record, juego.getJugador().getNombre().toUpperCase());
				}
				
				System.out.print("¿Quieres volver a jugar? (si/no): ");
				String respuesta = scanner.nextLine().toLowerCase();
				jugarDeNuevo = respuesta.startsWith("s");
				System.out.println("================================================");
			}
			
		} while (jugarDeNuevo);

		System.out.println("¡GRACIAS POR JUGAR!");
		System.out.println("...FIN DEL JUEGO");
		System.out.println("================================================");
		scanner.close();
	}

}