package fr.pizzeria.ihm.menu.option;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaDaoImpl;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.model.Pizza;

public class SupprimerPizzaOptionMenuTest {

	private SupprimerPizzaOptionMenu supprimerPizzaOptionMenu;
	private IPizzaDao dao;
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	@Rule
	public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

	@Before
	public void setUp() {
		Scanner scan = new Scanner(System.in);
		dao = new PizzaDaoImpl();
		supprimerPizzaOptionMenu = new SupprimerPizzaOptionMenu(dao, scan);
	}

	@Test
	public void testExecuteCodeExistant() throws DaoException {
		StringBuilder outAttendus = new StringBuilder();
		outAttendus.append("Suppression d’une pizza\r\n");
		List<Pizza> listPizzas = dao.findAllPizzas();
		listPizzas.stream().forEach(p -> {
			outAttendus.append(p).append("\r\n");
		});
		outAttendus.append("------- " + Pizza.nbPizzas + " pizzas créées depuis l’initialisation du programme\r\n"
				+ "Veuillez choisir le code la pizza à supprimer.\r\n" + "(99 pour abandonner).\r\n");

		systemInMock.provideLines("FRO");
		boolean next = supprimerPizzaOptionMenu.execute();
		assertTrue(next == false);
		assertEquals(outAttendus.toString(), systemOutRule.getLog());
	}

	@Test(expected = DeletePizzaException.class)
	public void testExecuteCodeInexistant() throws DaoException {
		systemInMock.provideLines("NEW");
		supprimerPizzaOptionMenu.execute();
	}
}
