package fr.pizzeria.dao.client;

import java.util.List;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Client;

/**
 * Interface de DAO pour la gestion des {@link Client}.
 */
public interface IClientDao {

	/**
	 * Récupere la liste des clients.
	 * 
	 * @return Une {@link List}<{@link Client}>.
	 */
	List<Client> findAllClients();

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
	 * Sauvegarde un nouveau client.
	 * 
	 * @param client Le nouveau client à sauvegarder.
	 * @throws DaoException
	 */
	void saveNewClient(Client client) throws DaoException;

	/**
	 * Modifie un client.
	 * 
	 * @param idClient L'id du client à modifier.
	 * @param client Le client modifié.
	 * @throws DaoException
	 */
	void updateClient(Integer idClient, Client client) throws DaoException;

	/**
	 * Supprime une client.
	 * 
	 * @param idClient L'id du client à supprimer.
	 * @throws DaoException
	 */
	void deleteClient(Integer idClient) throws DaoException;
}
