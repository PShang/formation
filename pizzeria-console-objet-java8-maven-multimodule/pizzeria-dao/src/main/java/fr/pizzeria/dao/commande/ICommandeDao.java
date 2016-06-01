package fr.pizzeria.dao.commande;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Commande;

/**
 * Interface de DAO pour la gestion des {@link Commande}.
 */
@FunctionalInterface
public interface ICommandeDao {

	/**
	 * Sauvegarde une nouvelle commande.
	 * 
	 * @param client La nouvelle commande à sauvegarder.
	 * @throws DaoException
	 */
	void saveNewCommande(Commande commande) throws DaoException;
}
