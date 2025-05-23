package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clases.Enemigo;
import clases.Guerrero;

public class TestGuerrero {

	static final int VIDA_INICIAL_ENEMIGO = 30;
	static final int ATAQUE_ENEMIGO = 6;
	static final int DEFENSA_ENEMIGO = 3;

	static final int VIDA_INICIAL_GUERRERO = 50;
	static final int CONFIGURAR_VIDA = 10;
	static final int POCIONES = 2;
	

	Guerrero guerrero;
	Enemigo enemigo;

	@BeforeEach
	public void setUp() {
		guerrero = new Guerrero("Kelly", VIDA_INICIAL_GUERRERO);
		enemigo = new Enemigo();
		enemigo.setNombre("Carlos");
		enemigo.setVida(VIDA_INICIAL_ENEMIGO);
		enemigo.setVidaInicial(VIDA_INICIAL_ENEMIGO);
		enemigo.setAtaque(ATAQUE_ENEMIGO);
		enemigo.setDefensa(DEFENSA_ENEMIGO); // Esto es importante para calcular el daño
	}

	@Test
	void testAtacar() {
		int vidaAntes = enemigo.getVida();
		guerrero.atacar(enemigo);
		int dañoEsperado = guerrero.getAtaque() - enemigo.getDefensa(); // 15 - 3 = 12
		assertEquals(vidaAntes - dañoEsperado, enemigo.getVida()); // El enemigo debe perder la vida esperada cuando el
																	// guerrero ataque
	}
	
	@Test
	public void testResetearDePersonaje() {
	    guerrero.setVida(CONFIGURAR_VIDA);
	    guerrero.resetear();  // Llamamos al resetear del Guerrero
	    assertEquals(CONFIGURAR_VIDA, guerrero.getVida(), "La vida no debe cambiar porque Guerrero.resetear() no restaura la vida.");
	}

	@Test
	void testResetearPociones() {
		guerrero.curar();
		assertEquals(POCIONES - 1, guerrero.getPociones());
		guerrero.resetear();
		assertEquals(POCIONES,guerrero.getPociones());
	}
	
	@Test
	public void testCurarSinPociones() {
		guerrero.setVida(CONFIGURAR_VIDA);
		guerrero.curar();
		guerrero.curar(); // segunda cura
		guerrero.curar(); // sin pociones
		assertEquals(VIDA_INICIAL_GUERRERO, guerrero.getVida());
		assertEquals(0, guerrero.getPociones()); // No debe haber curado esta vez
	}
	
	@Test
	public void testCurarRestauraVida() {
	    guerrero.setVida(CONFIGURAR_VIDA); 
	    guerrero.curar(); // llama internamente a super.resetear()
	    assertEquals(VIDA_INICIAL_GUERRERO, guerrero.getVida(), "La vida debería restaurarse al máximo tras curarse.");
	}
}
