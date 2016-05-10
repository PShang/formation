package fr.pizzeria.dao;

import java.util.List;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Pizza;

/**
 * Interface de DAO pour la gestion des {@link Pizza}.
 */
public interface IPizzaDao {
	/**
	 * Récupere la liste des pizzas.
	 * 
	 * @return Une {@link List}<{@link Pizza}>.
	 */
	List<Pizza> findAllPizzas();

	/**
	 * Sauvegarde une nouvelle pizza.
	 * 
	 * @param pizza La nouvelle pizza à sauvegarder.
	 * @throws DaoException
	 */
	void saveNewPizza(Pizza pizza) throws DaoException;

	/**
	 * Modifie une pizza.
	 * 
	 * @param codePizza Le code de la pizza à modifier.
	 * @param pizza La pizza modifiée.
	 * @throws DaoException
	 */
	void updatePizza(String codePizza, Pizza pizza) throws DaoException;

	/**
	 * Supprime une pizza.
	 * 
	 * @param codePizza Le code de la pizza à supprimer.
	 * @throws DaoException
	 */
	void deletePizza(String codePizza) throws DaoException;
}
