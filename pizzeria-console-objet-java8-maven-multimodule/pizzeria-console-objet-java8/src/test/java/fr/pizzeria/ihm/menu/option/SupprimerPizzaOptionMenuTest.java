package fr.pizzeria.ihm.menu.option;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

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
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.ihm.menu.option.pizza.SupprimerPizzaOptionMenu;
import fr.pizzeria.model.Pizza;

public class SupprimerPizzaOptionMenuTest {

	private SupprimerPizzaOptionMenu supprimerPizzaOptionMenu;
	private IDaoFactory dao;
	@Rule public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	@Rule public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

	@Before
	public void setUp() {
		Locale.setDefault(Locale.FRENCH);
		Scanner scan = new Scanner(System.in);
		dao = DaoProducer.getDaoFactoryMemoire();
		supprimerPizzaOptionMenu = new SupprimerPizzaOptionMenu(dao, scan);
	}

	@Test
	public void testExecuteCodeExistant() throws DaoException {
		StringBuilder outAttendus = new StringBuilder();
		outAttendus.append("Suppression d'une pizza" + System.lineSeparator());
		List<Pizza> listPizzas = dao.getPizzaDao().findAllPizzas();
		listPizzas.stream().forEach(p -> {
			outAttendus.append(p).append(System.lineSeparator());
		});
		outAttendus.append("------- " + Pizza.nbPizzas + " pizzas créées depuis l'initialisation du programme" + System.lineSeparator() + System.lineSeparator()
				+ "Veuillez choisir le code la pizza à supprimer." + System.lineSeparator() + "(99 pour abandonner)." + System.lineSeparator() + System.lineSeparator());

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
