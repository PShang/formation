package fr.pizzeria.ihm.menu.option;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaDaoImpl;
import fr.pizzeria.model.Pizza;

public class ListerPizzaOptionMenuTest {

	private ListerPizzaOptionMenu listerPizzaOptionMenu;
	private IPizzaDao dao;
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Before
	public void setUp() {
		dao = new PizzaDaoImpl();
		listerPizzaOptionMenu = new ListerPizzaOptionMenu(dao);
	}

	@Test
	public void testExecuteCodeExistant() {
		StringBuilder outAttendus = new StringBuilder();
		List<Pizza> listPizzas = dao.findAllPizzas();
		listPizzas.stream().forEach(p -> {
			outAttendus.append(p).append("\r\n");
		});
		outAttendus.append("------- " + Pizza.nbPizzas + " pizzas créées depuis l’initialisation du programme\r\n");

		boolean next = listerPizzaOptionMenu.execute();
		assertTrue(next == false);
		assertEquals(outAttendus.toString(), systemOutRule.getLog());
	}
}
