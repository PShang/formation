package fr.pizzeria.dao;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.pizzeria.dao.pizza.IPizzaDao;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AspectTest {

	protected IPizzaDao pizzaDao;

	@Before
	public void setUp() {
		Locale.setDefault(Locale.FRENCH);
	}

	@Test
	public void testSaveNewPizzaNoCode() throws DaoException {
		Pizza nullpizza = new Pizza(null, "Abcdef", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		Pizza emptyPizza = new Pizza("", "Ghijkl", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		pizzaDao.saveNewPizza(nullpizza);
		pizzaDao.saveNewPizza(emptyPizza);
		assertNotNull(pizzaDao.getPizza("ABC"));
		assertNotNull(pizzaDao.getPizza("GHI"));
	}
}
