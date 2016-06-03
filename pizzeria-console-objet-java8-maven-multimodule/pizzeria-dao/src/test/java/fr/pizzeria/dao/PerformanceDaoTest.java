package fr.pizzeria.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.pizzeria.dao.performance.IPerformanceDao;
import fr.pizzeria.dao.pizza.IPizzaDao;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Performance;
import fr.pizzeria.model.Pizza;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class PerformanceDaoTest {

	private static final int INITIAL_NUMBER_OF_PERFORMANCE = 4;

	protected IPerformanceDao performanceDao;
	protected IPizzaDao pizzaDao;

	@Before
	public void setUp() {
		Locale.setDefault(Locale.FRENCH);
	}

	@Test
	public void testFindAllPerformances() {
		List<Performance> performances = performanceDao.findAllPerformances();
		Assert.assertEquals(INITIAL_NUMBER_OF_PERFORMANCE, performances.size());
	}

	@Test
	public void testSaveNewPerformance() throws DaoException {
		Pizza newPizza = new Pizza("CODEINEX", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		pizzaDao.saveNewPizza(newPizza);
		List<Pizza> listPizzas = pizzaDao.findAllPizzas();
		assertTrue(listPizzas.contains(newPizza));

		List<Performance> listPerformance = performanceDao.findAllPerformances();
		assertEquals(INITIAL_NUMBER_OF_PERFORMANCE + 2, listPerformance.size());
	}
}
