package fr.pizzeria.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PizzaDaoSpringTest.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PizzaDaoJdbcTemplateImplTest {
	private IDaoFactory dao;

	@Before
	public void setUp() {
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dao-jdbc-template-config-test.xml")) {
			dao = context.getBean(IDaoFactory.class);
		}
	}

	@Test
	public void testFindAllPizzas() {
		// List<Pizza> pizzas = dao.getPizzaDao().findAllPizzas();
		// Assert.assertEquals(11, pizzas.size());
	}

	// @Test
	// public void testSaveNewPizzaCodeInexistant() throws DaoException {
	// Pizza newPizza = new Pizza("CODEINEX", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
	// dao.getPizzaDao().saveNewPizza(newPizza);
	// List<Pizza> listPizzas = dao.getPizzaDao().findAllPizzas();
	// assertTrue(listPizzas.contains(newPizza));
	// }
	//
	// @Test(expected = Exception.class)
	// public void testSaveNewPizzaCodeExistant() throws DaoException {
	// Pizza newPizza = new Pizza("FRO", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
	// dao.getPizzaDao().saveNewPizza(newPizza);
	// }
	//
	// @Test(expected = Exception.class)
	// public void testUpdatePizzaCodeInexistant() throws DaoException {
	// Pizza newPizza = new Pizza("CODEINEX", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
	// dao.getPizzaDao().updatePizza("CODEINEX", newPizza);
	// }
	//
	// @Test
	// public void testUpdatePizzaCodeExistant() throws DaoException {
	// Pizza newPizza = new Pizza("FRO", "nom", BigDecimal.valueOf(11), CategoriePizza.POISSON);
	// dao.getPizzaDao().updatePizza("FRO", newPizza);
	// List<Pizza> listPizzas = dao.getPizzaDao().findAllPizzas();
	// Optional<Pizza> pizzaOptional = listPizzas.stream().filter(p -> p.getCode().equals("FRO")).findFirst();
	// assertTrue(pizzaOptional.isPresent());
	// assertTrue(pizzaOptional.get().equals(newPizza));
	// }
	//
	// @Test(expected = Exception.class)
	// public void testDeletePizzaCodeInexistant() throws DaoException {
	// dao.getPizzaDao().deletePizza("CODEINEX");
	// }
	//
	// @Test
	// public void testDeletePizzaCodeExistant() throws DaoException {
	// dao.getPizzaDao().deletePizza("FRO");
	// List<Pizza> listPizzas = dao.getPizzaDao().findAllPizzas();
	// Optional<Pizza> pizzaOptional = listPizzas.stream().filter(p -> p.getCode().equals("FRO")).findFirst();
	// assertTrue(pizzaOptional.isPresent() == false);
	// }
}
