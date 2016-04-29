package fr.pizzeria.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Pizza;

public class PizzaDaoImpl implements IPizzaDao {

	private Map<String, Pizza> pizzas;

	public PizzaDaoImpl() {
		pizzas = new HashMap<String, Pizza>();
		pizzas.put("PEP", new Pizza("PEP", "Pépéroni", 12.50));
		pizzas.put("MAR", new Pizza("MAR", "Margherita", 14.00));
		pizzas.put("REI", new Pizza("REI", "La Reine", 11.50));
		pizzas.put("FRO", new Pizza("FRO", "La 4 fromages", 12.00));
		pizzas.put("CAN", new Pizza("CAN", "La cannibale", 12.50));
		pizzas.put("SAV", new Pizza("SAV", "La savoyarde", 13.00));
		pizzas.put("ORI", new Pizza("ORI", "L'orientale", 13.50));
		pizzas.put("IND", new Pizza("IND", "L'indienne", 14.00));
	}

	@Override
	public List<Pizza> findAllPizzas() {
		return new ArrayList<Pizza>(pizzas.values());
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws DaoException {
		if (pizzas.containsKey(pizza.getCode())) {
			throw new DaoException();
		}
		pizzas.put(pizza.getCode(), pizza);
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		if (!pizzas.containsKey(pizza.getCode())) {
			throw new DaoException();
		}
		pizzas.put(codePizza, pizza);
	}

	@Override
	public void deletePizza(String codePizza) throws DaoException {
		if (!pizzas.containsKey(codePizza)) {
			throw new DaoException();
		}
		pizzas.remove(codePizza);
	}
}
