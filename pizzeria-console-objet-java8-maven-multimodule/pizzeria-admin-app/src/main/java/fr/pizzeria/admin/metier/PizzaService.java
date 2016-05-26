package fr.pizzeria.admin.metier;

import java.util.List;

import javax.inject.Inject;

import fr.pizzeria.dao.pizza.IPizzaDao;
import fr.pizzeria.dao.pizza.PizzaDaoFichierImpl;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Pizza;

public class PizzaService {
	@Inject private IPizzaDao pizzaDao;

	public List<Pizza> findAllPizzas() {
		return pizzaDao.findAllPizzas();
	}

	public Pizza getPizza(String code) throws DaoException {
		return pizzaDao.getPizza(code);
	}

	public void saveNewPizza(Pizza pizza) throws DaoException {
		pizzaDao.saveNewPizza(pizza);
	}

	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		pizzaDao.updatePizza(codePizza, pizza);
	}

	public void deletePizza(String codePizza) throws DaoException {
		pizzaDao.deletePizza(codePizza);
	}

	public void importFromFiles(PizzaDaoFichierImpl pizzaDaoFichierImpl, int nb) throws DaoException {
		pizzaDao.importFromFiles(pizzaDaoFichierImpl, nb);
	}
}
