package fr.pizzeria.ihm.menu.option;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import fr.pizzeria.dao.DaoProducer;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.ihm.menu.option.pizza.ModifierPizzaOptionMenu;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class ModifierPizzaOptionMenuTest {

	private ModifierPizzaOptionMenu modifierPizzaOptionMenu;
	private IDaoFactory dao;
	@Rule public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	@Rule public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

	@Before
	public void setUp() {
		Locale.setDefault(Locale.FRENCH);
		Scanner scan = new Scanner(System.in);
		dao = DaoProducer.getDaoFactoryMemoire();
		modifierPizzaOptionMenu = new ModifierPizzaOptionMenu(dao, scan);
	}

	@Test
	public void testExecuteCodeExistant() throws DaoException {
		StringBuilder outAttendus = new StringBuilder();
		outAttendus.append("Mise à jour d'une pizza" + System.lineSeparator());
		List<Pizza> listPizzas = dao.getPizzaDao().findAllPizzas();
		listPizzas.stream().forEach(p -> {
			outAttendus.append(p).append(System.lineSeparator());
		});
		outAttendus.append(
				"------- " + Pizza.nbPizzas + " pizzas créées depuis l'initialisation du programme" + System.lineSeparator() + System.lineSeparator() + "Veuillez choisir le code la pizza à modifier."
						+ System.lineSeparator() + "(99 pour abandonner)." + System.lineSeparator() + "Veuillez saisir le nom (sans espace)" + System.lineSeparator() + "Veuillez saisir le prix"
						+ System.lineSeparator() + "Veuillez saisir la catégorie : " + Arrays.toString(CategoriePizza.values()) + System.lineSeparator() + System.lineSeparator());

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
