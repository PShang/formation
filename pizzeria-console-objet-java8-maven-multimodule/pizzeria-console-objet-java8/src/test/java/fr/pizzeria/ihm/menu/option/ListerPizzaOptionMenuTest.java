package fr.pizzeria.ihm.menu.option;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import fr.pizzeria.dao.DaoProducer;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.ihm.menu.option.pizza.ListerPizzaOptionMenu;
import fr.pizzeria.model.Pizza;

public class ListerPizzaOptionMenuTest {

	private ListerPizzaOptionMenu listerPizzaOptionMenu;
	private IDaoFactory dao;
	@Rule public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Before
	public void setUp() {
		Locale.setDefault(Locale.FRENCH);
		dao = DaoProducer.getDaoFactoryMemoire();
		listerPizzaOptionMenu = new ListerPizzaOptionMenu(dao);
	}

	@Test
	public void testExecuteCodeExistant() {
		StringBuilder outAttendus = new StringBuilder();
		List<Pizza> listPizzas = dao.getPizzaDao().findAllPizzas();
		listPizzas.stream().forEach(p -> {
			outAttendus.append(p).append(System.lineSeparator());
		});
		outAttendus.append("------- " + Pizza.nbPizzas + " pizzas créées depuis l'initialisation du programme" + System.lineSeparator() + System.lineSeparator());

		boolean next = listerPizzaOptionMenu.execute();
		assertTrue(next == false);
		assertEquals(outAttendus.toString(), systemOutRule.getLog());
	}
}
