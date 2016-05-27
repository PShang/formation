package fr.pizzeria.dao.client;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Client;

public class ClientDaoRestImpl implements IClientDao {
	private String baseUrl;

	public ClientDaoRestImpl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public void saveNewClient(Client client) throws DaoException {
		throw new UnsupportedOperationException("La méthode n'est pas encore implémentée.");

	}

	@Override
	public Client getClient(String email, String mdp) throws DaoException {
		throw new UnsupportedOperationException("La méthode n'est pas encore implémentée.");
	}

}
