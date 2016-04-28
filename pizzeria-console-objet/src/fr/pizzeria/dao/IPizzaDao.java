package fr.pizzeria.dao;

import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.Pizza;

public interface IPizzaDao {
	Pizza[] findAllPizzas();

	void saveNewPizza(Pizza pizza) throws SavePizzaException;

	void updatePizza(String codePizza, Pizza pizza) throws UpdatePizzaException;

	void deletePizza(String codePizza) throws DeletePizzaException;
}
