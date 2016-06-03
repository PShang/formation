package fr.pizzeria.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
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

	public static final int INITIAL_NUMBER_OF_PIZZA = 11;

	protected IPizzaDao pizzaDao;

	@Before
	public void setUp() {
		Locale.setDefault(Locale.FRENCH);
	}

	@Test
	public void testFindAllPizzas() {
		List<Pizza> pizzas = pizzaDao.findAllPizzas();
		Assert.assertEquals(INITIAL_NUMBER_OF_PIZZA, pizzas.size());
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

	@Test
	public void testSaveAllPizza() throws DaoException {
		List<Pizza> pizzas = getListePizzas();
		pizzaDao.saveAllPizzas(pizzas, 3);

		List<Pizza> pizzasBdd = pizzaDao.findAllPizzas();
		assertEquals(INITIAL_NUMBER_OF_PIZZA + pizzas.size(), pizzasBdd.size());
	}

	@Test
	public void testSaveAllPizzaRollback() throws DaoException {
		List<Pizza> pizzas = getListePizzasWithErrors();
		try {
			pizzaDao.saveAllPizzas(pizzas, 7);
			fail("une exception aurait dû être lancée");
		} catch (Exception e) {
			assertNotNull(pizzaDao.getPizza("PIZZA1"));
			assertNotNull(pizzaDao.getPizza("PIZZA2"));
			assertNotNull(pizzaDao.getPizza("PIZZA3"));
			assertNotNull(pizzaDao.getPizza("PIZZA4"));
			assertNotNull(pizzaDao.getPizza("PIZZA5"));
			assertNotNull(pizzaDao.getPizza("PIZZA6"));
			assertNotNull(pizzaDao.getPizza("PIZZA7"));
			assertNull(pizzaDao.getPizza("PIZZA8"));
			assertNull(pizzaDao.getPizza("PIZZA9"));
		}
	}

	private List<Pizza> getListePizzas() {
		List<Pizza> pizzas = new ArrayList<>();
		pizzas.add(new Pizza(null, "PIZZA1", "Pizza 1", BigDecimal.valueOf(12.50), CategoriePizza.VIANDE, "http://placehold.it/150x150"));
		pizzas.add(new Pizza(null, "PIZZA2", "Pizza 2", BigDecimal.valueOf(14.00), CategoriePizza.SANS_VIANDE, "http://placehold.it/150x150"));
		pizzas.add(new Pizza(null, "PIZZA3", "Pizza 3", BigDecimal.valueOf(11.50), CategoriePizza.VIANDE, "http://placehold.it/150x150"));
		pizzas.add(new Pizza(null, "PIZZA4", "Pizza 4", BigDecimal.valueOf(12.00), CategoriePizza.SANS_VIANDE, "http://placehold.it/150x150"));
		pizzas.add(new Pizza(null, "PIZZA5", "Pizza 5", BigDecimal.valueOf(12.50), CategoriePizza.VIANDE, "http://placehold.it/150x150"));
		pizzas.add(new Pizza(null, "PIZZA6", "Pizza 6", BigDecimal.valueOf(13.00), CategoriePizza.VIANDE, "http://placehold.it/150x150"));
		pizzas.add(new Pizza(null, "PIZZA7", "Pizza 7", BigDecimal.valueOf(13.50), CategoriePizza.VIANDE, "http://placehold.it/150x150"));
		pizzas.add(new Pizza(null, "PIZZA8", "Pizza 8", BigDecimal.valueOf(14.00), CategoriePizza.VIANDE, "http://placehold.it/150x150"));
		pizzas.add(new Pizza(null, "PIZZA9", "Pizza 9", BigDecimal.valueOf(14.00), CategoriePizza.POISSON, "http://placehold.it/150x150"));
		return pizzas;
	}

	private List<Pizza> getListePizzasWithErrors() {
		List<Pizza> pizzas = getListePizzas();
		pizzas.add(new Pizza(null, null, "Pizza 10", BigDecimal.valueOf(14.00), CategoriePizza.VIANDE, "http://placehold.it/150x150"));
		return pizzas;
	}
}
