package fr.pizzeria.dao;

import fr.pizzeria.model.Pizza;

public class PizzaDaoImpl implements IPizzaDao {

	private Pizza[] pizzas;

	public PizzaDaoImpl() {
		pizzas = new Pizza[] { new Pizza("PEP", "P�p�roni", 12.50), new Pizza("MAR", "Margherita", 14.00),
				new Pizza("REI", "La Reine", 11.50), new Pizza("FRO", "La 4 fromages", 12.00),
				new Pizza("CAN", "La cannibale", 12.50), new Pizza("SAV", "La savoyarde", 13.00),
				new Pizza("ORI", "L'orientale", 13.50), new Pizza("IND", "L'indienne", 14.00) };
	}

	/**
	 * V�rifie si la pizza existe.
	 *
	 * @param code Le code de la pizza � chercher.
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
	public Pizza[] findAllPizzas() {
		return pizzas.clone();
	}

	@Override
	public boolean saveNewPizza(Pizza pizza) {
		if (pizzaExists(pizza.getCode())) {
			return false;
		}
		int nb_pizza = pizzas.length;
		Pizza[] p = new Pizza[nb_pizza + 1];
		int i = 0;
		while (i < nb_pizza) {
			p[i] = pizzas[i++];
		}
		p[i] = pizza;
		pizzas = p;
		return true;
	}

	@Override
	public boolean updatePizza(String codePizza, Pizza pizza) {
		if (!pizzaExists(pizza.getCode())) {
			return false;
		}
		for (int i = 0; i < pizzas.length; ++i) {
			if (pizzas[i].getCode().equals(codePizza)) {
				pizzas[i] = pizza;
			}
		}
		return true;
	}

	@Override
	public boolean deletePizza(String codePizza) {
		if (!pizzaExists(codePizza)) {
			return false;
		}
		int nb_pizza = pizzas.length;
		Pizza[] p = new Pizza[nb_pizza - 1];
		int i = 0;
		int j = 0;
		while (i < nb_pizza) {
			if (!pizzas[i].getCode().equals(codePizza)) {
				p[i - j] = pizzas[i];
			} else {
				++j;
			}
			++i;
		}
		pizzas = p;
		return true;
	}
}
