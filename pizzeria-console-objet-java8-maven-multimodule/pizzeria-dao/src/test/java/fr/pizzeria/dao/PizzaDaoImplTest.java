package fr.pizzeria.dao;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import fr.pizzeria.dao.pizza.IPizzaDao;
import fr.pizzeria.dao.pizza.PizzaDaoMemoireImpl;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class PizzaDaoImplTest {
	private IPizzaDao dao;

	@Before
	public void setUp() {
		Locale.setDefault(Locale.FRENCH);
		dao = new PizzaDaoMemoireImpl();
	}

	@Test
	public void testFindAllPizzas() {
		String placeholderImg = "http://placehold.it/150x150";
		List<Pizza> pizzas = new ArrayList<Pizza>();
		pizzas.add(new Pizza(1, "PEP", "Pépéroni", BigDecimal.valueOf(12.50), CategoriePizza.VIANDE, placeholderImg));
		pizzas.add(new Pizza(2, "MAR", "Margherita", BigDecimal.valueOf(14.00), CategoriePizza.SANS_VIANDE, placeholderImg));
		pizzas.add(new Pizza(3, "REI", "La Reine", BigDecimal.valueOf(11.50), CategoriePizza.VIANDE, placeholderImg));
		pizzas.add(new Pizza(4, "FRO", "La 4 fromages", BigDecimal.valueOf(12.00), CategoriePizza.SANS_VIANDE, placeholderImg));
		pizzas.add(new Pizza(5, "CAN", "La cannibale", BigDecimal.valueOf(12.50), CategoriePizza.VIANDE, placeholderImg));
		pizzas.add(new Pizza(6, "SAV", "La savoyarde", BigDecimal.valueOf(13.00), CategoriePizza.VIANDE, placeholderImg));
		pizzas.add(new Pizza(7, "ORI", "L'orientale", BigDecimal.valueOf(13.50), CategoriePizza.VIANDE, placeholderImg));
		pizzas.add(new Pizza(8, "IND", "L'indienne", BigDecimal.valueOf(14.00), CategoriePizza.VIANDE, placeholderImg));
		pizzas.add(new Pizza(9, "SAU", "La saumonetta", BigDecimal.valueOf(15.50), CategoriePizza.POISSON, placeholderImg));

		Collections.sort(pizzas, Comparator.comparing(Pizza::getNom));
		List<Pizza> resultat = dao.findAllPizzas();
		assertArrayEquals(pizzas.toArray(), resultat.toArray());
	}

	@Test
	public void testSaveNewPizzaCodeInexistant() throws DaoException {
		Pizza newPizza = new Pizza("CODEINEX", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		dao.saveNewPizza(newPizza);
		List<Pizza> listPizzas = dao.findAllPizzas();
		assertTrue(listPizzas.contains(newPizza));
	}

	@Test(expected = SavePizzaException.class)
	public void testSaveNewPizzaCodeExistant() throws DaoException {
		Pizza newPizza = new Pizza("FRO", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		dao.saveNewPizza(newPizza);
	}

	@Test(expected = UpdatePizzaException.class)
	public void testUpdatePizzaCodeInexistant() throws DaoException {
		Pizza newPizza = new Pizza("CODEINEX", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		dao.updatePizza("CODEINEX", newPizza);
	}

	@Test
	public void testUpdatePizzaCodeExistant() throws DaoException {
		Pizza newPizza = new Pizza("FRO", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
		dao.updatePizza("FRO", newPizza);
		List<Pizza> listPizzas = dao.findAllPizzas();
		Optional<Pizza> pizzaOptional = listPizzas.stream().filter(p -> p.getCode().equals("FRO")).findFirst();
		assertTrue(pizzaOptional.isPresent());
		assertTrue(pizzaOptional.get().equals(newPizza));
	}

	@Test(expected = DeletePizzaException.class)
	public void testDeletePizzaCodeInexistant() throws DaoException {
		dao.deletePizza("CODEINEX");
	}

	@Test
	public void testDeletePizzaCodeExistant() throws DaoException {
		dao.deletePizza("FRO");
		List<Pizza> listPizzas = dao.findAllPizzas();
		Optional<Pizza> pizzaOptional = listPizzas.stream().filter(p -> p.getCode().equals("FRO")).findFirst();
		assertTrue(pizzaOptional.isPresent() == false);
	}
}
