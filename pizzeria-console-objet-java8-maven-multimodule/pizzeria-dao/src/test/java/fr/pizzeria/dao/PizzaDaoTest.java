package fr.pizzeria.dao;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dao-jdbc-template-config-test.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class PizzaDaoTest {
	protected IDaoFactory dao;

	@Test
	public void testFindAllPizzas() {
		List<Pizza> pizzas = dao.getPizzaDao().findAllPizzas();
		Assert.assertEquals(11, pizzas.size());
	}

	@Test
	public void testSaveNewPizzaCodeInexistant() throws DaoException {
		Pizza newPizza = new Pizza("CODEINEX", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		dao.getPizzaDao().saveNewPizza(newPizza);
		List<Pizza> listPizzas = dao.getPizzaDao().findAllPizzas();
		assertTrue(listPizzas.contains(newPizza));
	}

	@Test(expected = SavePizzaException.class)
	public void testSaveNewPizzaCodeExistant() throws DaoException {
		Pizza newPizza = new Pizza("FRO", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		dao.getPizzaDao().saveNewPizza(newPizza);
	}

	@Test(expected = UpdatePizzaException.class)
	public void testUpdatePizzaCodeInexistant() throws DaoException {
		Pizza newPizza = new Pizza("CODEINEX", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		dao.getPizzaDao().updatePizza("CODEINEX", newPizza);
	}

	@Test
	public void testUpdatePizzaCodeExistant() throws DaoException {
		Pizza newPizza = new Pizza("FRO", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		dao.getPizzaDao().updatePizza("FRO", newPizza);
		List<Pizza> listPizzas = dao.getPizzaDao().findAllPizzas();
		Optional<Pizza> pizzaOptional = listPizzas.stream().filter(p -> p.getCode().equals("FRO")).findFirst();
		assertTrue(pizzaOptional.isPresent());
		assertTrue(pizzaOptional.get().equals(newPizza));
	}

	@Test(expected = DeletePizzaException.class)
	public void testDeletePizzaCodeInexistant() throws DaoException {
		dao.getPizzaDao().deletePizza("CODEINEX");
	}

	@Test
	public void testDeletePizzaCodeExistant() throws DaoException {
		dao.getPizzaDao().deletePizza("FRO");
		List<Pizza> listPizzas = dao.getPizzaDao().findAllPizzas();
		Optional<Pizza> pizzaOptional = listPizzas.stream().filter(p -> p.getCode().equals("FRO")).findFirst();
		assertTrue(pizzaOptional.isPresent() == false);
	}
}
