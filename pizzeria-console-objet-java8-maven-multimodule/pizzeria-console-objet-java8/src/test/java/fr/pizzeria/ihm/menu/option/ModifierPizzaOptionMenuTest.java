package fr.pizzeria.ihm.menu.option;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import java.util.Arrays;
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
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class ModifierPizzaOptionMenuTest {

	private ModifierPizzaOptionMenu modifierPizzaOptionMenu;
	private IPizzaDao dao;
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	@Rule
	public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

	@Before
	public void setUp() {
		Scanner scan = new Scanner(System.in);
		dao = new PizzaDaoImpl();
		modifierPizzaOptionMenu = new ModifierPizzaOptionMenu(dao, scan);
	}

	@Test
	public void testExecuteCodeExistant() throws DaoException {
		StringBuilder outAttendus = new StringBuilder();
		outAttendus.append("Mise à jour d’une pizza\r\n");
		List<Pizza> listPizzas = dao.findAllPizzas();
		listPizzas.stream().forEach(p -> {
			outAttendus.append(p).append("\r\n");
		});
		outAttendus.append("------- " + Pizza.nbPizzas + " pizzas créées depuis l’initialisation du programme\r\n"
				+ "Veuillez choisir le code la pizza à modifier.\r\n" + "(99 pour abandonner).\r\n"
				+ "Veuillez saisir le nom (sans espace)\r\n" + "Veuillez saisir le prix\r\n"
				+ "Veuillez saisir la catégorie : " + Arrays.toString(CategoriePizza.values()) + "\r\n");

		systemInMock.provideLines("FRO", "fromage", "20,6", "sans_viande");
		boolean next = modifierPizzaOptionMenu.execute();
		assertTrue(next == false);
		assertEquals(outAttendus.toString(), systemOutRule.getLog());
	}

	@Test(expected = UpdatePizzaException.class)
	public void testExecuteCodeInexistant() throws DaoException {
		systemInMock.provideLines("NEW", "NewPizza", "12,5", "viande");
		modifierPizzaOptionMenu.execute();
	}
}
