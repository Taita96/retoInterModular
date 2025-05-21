package clases;

public class Enemigo extends Personaje {

	public Enemigo() {}
	
	public void iniciarEnemigo(String nombre) {
		super.setNombre(nombre);
		super.setVida(Metodos.aleatorio(20,100));
		super.setVidaInicial(getVida());
		super.setAtaque(Metodos.aleatorio(2,10));
		super.setDefensa(Metodos.aleatorio(1,3));
	}

}
