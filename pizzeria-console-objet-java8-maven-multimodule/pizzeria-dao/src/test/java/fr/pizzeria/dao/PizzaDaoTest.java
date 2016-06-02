package fr.pizzeria.dao;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.Assert;
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
public abstract class PizzaDaoTest {

	protected IPizzaDao pizzaDao;

	@Before
	public void setUp() {
		Locale.setDefault(Locale.FRENCH);
	}

	@Test
	public void testFindAllPizzas() {
		List<Pizza> pizzas = pizzaDao.findAllPizzas();
		Assert.assertEquals(11, pizzas.size());
	}

	@Test
	public void testSaveNewPizzaCodeInexistant() throws DaoException {
		Pizza newPizza = new Pizza("CODEINEX", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		pizzaDao.saveNewPizza(newPizza);
		List<Pizza> listPizzas = pizzaDao.findAllPizzas();
		assertTrue(listPizzas.contains(newPizza));
	}

	@Test(expected = Exception.class)
	public void testSaveNewPizzaCodeExistant() throws DaoException {
		Pizza newPizza = new Pizza("FRO", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		pizzaDao.saveNewPizza(newPizza);
	}

	@Test(expected = Exception.class)
	public void testUpdatePizzaCodeInexistant() throws DaoException {
		Pizza newPizza = new Pizza("CODEINEX", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		pizzaDao.updatePizza("CODEINEX", newPizza);
	}

	@Test
	public void testUpdatePizzaCodeExistant() throws DaoException {
		Pizza newPizza = new Pizza("FRO", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		pizzaDao.updatePizza("FRO", newPizza);
		List<Pizza> listPizzas = pizzaDao.findAllPizzas();
		Optional<Pizza> pizzaOptional = listPizzas.stream().filter(p -> p.getCode().equals("FRO")).findFirst();
		assertTrue(pizzaOptional.isPresent());
		assertTrue(pizzaOptional.get().equals(newPizza));
	}

	@Test(expected = Exception.class)
	public void testDeletePizzaCodeInexistant() throws DaoException {
		pizzaDao.deletePizza("CODEINEX");
	}

	@Test
	public void testDeletePizzaCodeExistant() throws DaoException {
		pizzaDao.deletePizza("FRO");
		List<Pizza> listPizzas = pizzaDao.findAllPizzas();
		Optional<Pizza> pizzaOptional = listPizzas.stream().filter(p -> p.getCode().equals("FRO")).findFirst();
		assertTrue(pizzaOptional.isPresent() == false);
	}
}
