package fr.pizzeria.dao.pizza;

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
	 * Récupere une pizza par son code.
	 * 
	 * @param code Le code de la pizza à récupérer.
	 * @return Une {@link Pizza}.
	 * @throws DaoException
	 */
	Pizza getPizza(String code) throws DaoException;

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

	/**
	 * Importe les pizzas depuis les fichiers.
	 * 
	 * @param pizzas La Liste des pizzas à ajouter.
	 * @param nb Le nombre de pizza par groupe d'importation.
	 * @throws DaoException
	 */
	void saveAllPizzas(List<Pizza> pizzas, int nb) throws DaoException;
}
