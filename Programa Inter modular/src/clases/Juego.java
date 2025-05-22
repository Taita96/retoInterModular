package clases;

import java.io.Serializable;
import java.util.ArrayList;

public class Juego implements Serializable{

	private static final long serialVersionUID = 627450528453243275L;
	private static String[] nombresEnemigos = { "Señor Mordisquitos", "El Gato con Navajas", "Carla la Celosa",
			"Conde Linux el Destructor", "El Espíritu del Wi-Fi Caído", "Reverendo Chicharrón",
			"La Excepción No Capturada", "Debugger el Oscuro", "Diego el Quita Puntos"};
	private ArrayList<Enemigo> enemigos;
	private Personaje jugador;
	private int nRondas;
	private int ronda;

	public Juego() {
		enemigos = new ArrayList<>();
	}
	
	public static String obtenerNombreEnemigoAleatorio() {
		return nombresEnemigos[Metodos.aleatorio(0, (nombresEnemigos.length - 1))];
	}

	public void iniciarJuego() {
		enemigos.clear();
		for (int i = 0; i < nRondas; i++) {
			Enemigo nuevoEnemigo = new Enemigo();
			nuevoEnemigo.iniciarEnemigo(obtenerNombreEnemigoAleatorio());
			enemigos.add(nuevoEnemigo);
		}
		ronda = 0;
	}

	public Enemigo getSiguiente() {
		if (!enemigos.isEmpty()) {
			return enemigos.get(0);
		}
		return null;
	}

	public boolean terminarRonda() {
	    Enemigo enemigoActual = getSiguiente();
	    if (enemigoActual != null && enemigoActual.muerto()) {
	        enemigos.remove(0);
	        ronda++;
	        return true;
	    }
	    return false;
	}
	
	public void nuevoGuerrero(String nombre) {
        Guerrero nuevoGuerrero = new Guerrero(nombre,Metodos.aleatorio(100, 200));
        jugador = nuevoGuerrero;
    }

    public void nuevoMago(String nombre) {
    	Mago nuevoMago = new Mago(nombre,Metodos.aleatorio(50, 150));
        jugador = nuevoMago;
    }
    
    public boolean finalJuego() {
        return enemigos.isEmpty();
    }
    
    public void eliminarEnemigos() {
    	enemigos.clear();
    }

	public Personaje getJugador() {
		return jugador;
	}

	public void setJugador(Personaje jugador) {
		this.jugador = jugador;
	}

	public int getnRondas() {
		return nRondas;
	}

	public void setnRondas(int nRondas) {
		this.nRondas = nRondas;
	}

	public int getRonda() {
		return ronda;
	}

	public void setRonda(int ronda) {
		this.ronda = ronda;
	}

	@Override
	public String toString() {
		Enemigo enemigoActual = enemigos.isEmpty() ? null : enemigos.get(0);
		return String.format("Juego [enemigo actual:%s (vida:%s), jugador:%s, nRondas:%s, ronda:%s]", 
			enemigoActual != null ? enemigoActual.getNombre() : "Ninguno",
			enemigoActual != null ? enemigoActual.getVida() : "N/A",
			jugador, nRondas, ronda);
	}
	
	
	
}
