package fr.pizzeria.dao.client;

import java.util.Set;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Client;
import fr.pizzeria.model.Commande;

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
	 * Récupère un client.
	 * 
	 * @param email L'adresse email du client à chercher.
	 * @param mdp Le mot de passe du client.
	 * @return L'id du client.
	 * @throws DaoException
	 */
	Client getClient(String email, String mdp) throws DaoException;

	/**
	 * Récupère la liste des commandes d'un client.
	 * 
	 * @param client Le client.
	 * @return La liste des commandes.
	 * @throws DaoException
	 */
	Set<Commande> getAllCommandes(Client client) throws DaoException;
}
