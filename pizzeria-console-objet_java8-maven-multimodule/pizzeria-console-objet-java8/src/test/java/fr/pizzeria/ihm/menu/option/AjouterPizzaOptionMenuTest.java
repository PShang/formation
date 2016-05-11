package fr.pizzeria.ihm.menu.option;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import java.util.Arrays;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaDaoImpl;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.model.CategoriePizza;

public class AjouterPizzaOptionMenuTest {

	private AjouterPizzaOptionMenu ajouterPizzaOptionMenu;
	private IPizzaDao dao;
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	@Rule
	public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

	@Before
	public void setUp() {
		Scanner scan = new Scanner(System.in);
		dao = new PizzaDaoImpl();
		ajouterPizzaOptionMenu = new AjouterPizzaOptionMenu(dao, scan);
	}

	@Test
	public void testExecuteCodeInexistant() throws DaoException {
		systemInMock.provideLines("NEW", "NewPizza", "12,5", "viande");
		boolean next = ajouterPizzaOptionMenu.execute();
		assertTrue(next == false);
		String outAttendus = "Ajout d’une nouvelle pizza\r\n" + "Veuillez saisir le code\r\n"
				+ "Veuillez saisir le nom (sans espace)\r\n" + "Veuillez saisir le prix\r\n"
				+ "Veuillez saisir la catégorie : " + Arrays.toString(CategoriePizza.values()) + "\r\n\r\n";
		assertEquals(outAttendus, systemOutRule.getLog());
	}

	@Test(expected = SavePizzaException.class)
	public void testExecuteCodeExistant() throws DaoException {
		systemInMock.provideLines("FRO", "NewPizza", "12,5", "viande");
		ajouterPizzaOptionMenu.execute();
	}
}
