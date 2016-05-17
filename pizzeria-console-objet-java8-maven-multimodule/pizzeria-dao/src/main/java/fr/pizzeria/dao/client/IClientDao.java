package fr.pizzeria.dao.client;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Client;

/**
 * Interface de DAO pour la gestion des {@link Client}.
 */
public interface IClientDao {

	/**
	 * Sauvegarde un nouveau client.
	 * 
	 * @param client Le nouveau client à sauvegarder.
	 * @throws DaoException
	 */
	void saveNewClient(Client client) throws DaoException;

	/**
	 * 
	 * @param email L'adresse email du client à chercher.
	 * @param mdp Le mot de passe du client.
	 * @return L'id du client.
	 * @throws DaoException
	 */
	Integer getClient(String email, String mdp) throws DaoException;
}
