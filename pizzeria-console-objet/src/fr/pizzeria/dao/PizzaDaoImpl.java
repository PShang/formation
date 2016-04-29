package fr.pizzeria.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Pizza;

public class PizzaDaoImpl implements IPizzaDao {

	private List<Pizza> pizzas;

	public PizzaDaoImpl() {
		pizzas = new ArrayList<Pizza>();
		pizzas.add(new Pizza("PEP", "Pépéroni", 12.50));
		pizzas.add(new Pizza("MAR", "Margherita", 14.00));
		pizzas.add(new Pizza("REI", "La Reine", 11.50));
		pizzas.add(new Pizza("FRO", "La 4 fromages", 12.00));
		pizzas.add(new Pizza("CAN", "La cannibale", 12.50));
		pizzas.add(new Pizza("SAV", "La savoyarde", 13.00));
		pizzas.add(new Pizza("ORI", "L'orientale", 13.50));
		pizzas.add(new Pizza("IND", "L'indienne", 14.00));
	}

	/**
	 * Vérifie si la pizza existe.
	 *
	 * @param code Le code de la pizza à chercher.
	 * @return {@code true} si la pizza existe.
	 */
	private boolean pizzaExists(String code) {
		for (Pizza pizza : pizzas) {
			if (pizza.getCode().equals(code))
				return true;
		}
		return false;
	}

	@Override
	public List<Pizza> findAllPizzas() {
		return new ArrayList<Pizza>(pizzas);
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws DaoException {
		if (pizzaExists(pizza.getCode())) {
			throw new DaoException();
		}
		pizzas.add(pizza);
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		if (!pizzaExists(pizza.getCode())) {
			throw new DaoException();
		}
		for (int i = 0; i < pizzas.size(); ++i) {
			if (pizzas.get(i).getCode().equals(codePizza)) {
				pizzas.set(i, pizza);
			}
		}
	}

	@Override
	public void deletePizza(String codePizza) throws DaoException {
		if (!pizzaExists(codePizza)) {
			throw new DaoException();
		}
		Iterator<Pizza> it = pizzas.iterator();
		while (it.hasNext()) {
			if (it.next().getCode().equals(codePizza)) {
				it.remove();
			}
		}
	}
}
