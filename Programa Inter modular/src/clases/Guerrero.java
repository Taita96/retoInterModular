package clases;

public class Guerrero extends Personaje implements Jugable {

	static final int FUERZA = 15; 
	static final int DEFENSA = 10; 
	static final int POCIONES = 2; 
	
	/**
	 * Cuántas veces podemos curarnos. 
	 */
	private int pociones;
	
	public Guerrero(String nombre, int vida) {
		super(nombre, vida, FUERZA, DEFENSA);
		pociones = POCIONES;
	}
	
	public void curar() {
		if (pociones > 0) {
			super.resetear();
			pociones--;
		}
	}
	
	public void resetear() {
		pociones = POCIONES;
	}
	
	public String toString() {
		return super.toString() + "; Pociones: " + pociones;
	}

}
