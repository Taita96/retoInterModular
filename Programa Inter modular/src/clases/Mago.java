package clases;

public class Mago extends Personaje {
	
	static final int FUERZA_MAGIA = 20; 
	static final int FUERZA_SIN_MAGIA = 5; 
	static final int DEFENSA = 5; 
	static final int MAGIA = 10; 
	
	/**
	 * Cuántas veces podemos usar magia. 
	 */
	private int magia;

	public Mago(String nombre, int vida) {
		super(nombre, vida, FUERZA_MAGIA, DEFENSA);
		magia = MAGIA;
	}
	
	public void atacar(Personaje otro) {
		super.atacar(otro);
		if (magia == 0) {
			otro.setAtaque(FUERZA_SIN_MAGIA);
		} else {
			magia--;
		}
	}
	
	public void curar() {
		if ((magia > 0) && (getVida() < getVidaInicial())) {
			setVida(getAtaque());
			magia--;
		} 
	}
	
	public void resetear() {
		magia = MAGIA;
	}
	
	public String toString() {
		return super.toString() + "; Magia: " + magia;
	}

}
