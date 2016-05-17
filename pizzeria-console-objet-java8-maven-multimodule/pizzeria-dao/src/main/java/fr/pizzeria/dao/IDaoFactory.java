package fr.pizzeria.dao;

import fr.pizzeria.dao.client.IClientDao;
import fr.pizzeria.dao.commande.ICommandeDao;
import fr.pizzeria.dao.pizza.IPizzaDao;

/**
 * Interface de Factory DAO.
 */
public interface IDaoFactory {

	/**
	 * Implémente {@link IPizzaDao}.
	 * 
	 * @return L'implémentation de {@link IPizzaDao}.
	 */
	IPizzaDao getPizzaDao();

	/**
	 * Implémente {@link IClientDao}.
	 * 
	 * @return L'implémentation de {@link IClientDao}.
	 */
	IClientDao getClientDao();

	/**
	 * Implémente {@link ICommandeDao}.
	 * 
	 * @return L'implémentation de {@link ICommandeDao}.
	 */
	ICommandeDao getCommandeDao();
}
