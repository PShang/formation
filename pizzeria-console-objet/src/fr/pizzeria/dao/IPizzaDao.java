package fr.pizzeria.dao;

import java.util.List;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Pizza;

public interface IPizzaDao {
	List<Pizza> findAllPizzas();

	void saveNewPizza(Pizza pizza) throws DaoException;

	void updatePizza(String codePizza, Pizza pizza) throws DaoException;

	void deletePizza(String codePizza) throws DaoException;
}
