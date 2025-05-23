package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clases.Enemigo;
import clases.Mago;

public class TestMago {

	static final int VIDA_INICIAL_ENEMIGO = 30;
	static final int VIDA_INICIAL_MAGO = 50;
	static final int ATAQUE_ENEMIGO = 6;
	static final int DEFENSA_ENEMIGO = 3;

	static final int FUERZA_MAGIA = 20;
	static final int CONFIGURAR_VIDA = 5;
	static final int MAGIA = 10;

	Mago mago;
	Enemigo enemigo;

	@BeforeEach
	public void setUp() {
		mago = new Mago("Kelly", VIDA_INICIAL_MAGO);
		enemigo = new Enemigo();
		enemigo.setNombre("Carlos");
		enemigo.setVida(VIDA_INICIAL_ENEMIGO);
		enemigo.setVidaInicial(VIDA_INICIAL_ENEMIGO);
		enemigo.setAtaque(ATAQUE_ENEMIGO);
		enemigo.setDefensa(DEFENSA_ENEMIGO); // Esto es importante para calcular el daño
	}

	@Test
	public void testAtacarConMagia() {
		int vidaAntes = enemigo.getVida();
		mago.atacar(enemigo);
		int dañoEsperado = Mago.getFuerzaMagia() - enemigo.getDefensa(); // 20 - 3 = 17
		assertEquals(vidaAntes - dañoEsperado, enemigo.getVida()); // El enemigo debe perder la vida esperada cuando el
																	// mago usa magia
	}

	@Test
	public void testAtacarSinMagia() {
		// Agotar toda la magia
		mago.setMagia(0);

		// Atacar al enemigo y verificar que ataque cambió a FUERZA_SIN_MAGIA
		mago.atacar(enemigo); // Ahora ya sin magia

		assertEquals(Mago.getFuerzaSinMagia(), enemigo.getAtaque(),
				"El enemigo debería tener ahora como ataque la FUERZA_SIN_MAGIA porque el mago no tiene magia.");
	}

	@Test
	public void testCurarConMagia() {
		mago.setVida(CONFIGURAR_VIDA);
		mago.curar();
		assertEquals(FUERZA_MAGIA, mago.getVida(), "Debe curarse al valor del ataque.");
	}

	@Test
	public void testCurarSinMagia() {
		// Agotar toda la magia
		mago.setMagia(0);
		mago.setVida(CONFIGURAR_VIDA);
		mago.curar();
		assertEquals(CONFIGURAR_VIDA, mago.getVida(), "No debe curarse si no hay magia.");
	}

	@Test
	public void testResetear() {
		mago.setMagia(0);
		assertEquals(0, mago.getMagia(), "Debe ser verdadero si magia es igual a 0");
		mago.resetear();
		assertEquals(MAGIA, mago.getMagia(), "Debe ser verdadero si es igual a 10");
	}
}
