package fr.pizzeria.admin.metier;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Client;

@Stateless
public class ClientService {
	@PersistenceContext(unitName = "pizzeria-admin-app") private EntityManager em;

	public List<Client> findAllClients() {
		return em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
	}

	public void saveNewClient(Client client) throws DaoException {
		em.persist(client);
	}

	public void updateClient(Integer id, Client client) throws DaoException {
		Client c = em.find(Client.class, id);
		c.setNom(client.getNom());
		c.setPrenom(client.getPrenom());
		c.setEmail(client.getEmail());
		c.setMdp(client.getMdp());
	}

	public void deleteClient(Integer id) throws DaoException {
		em.remove(em.find(Client.class, id));
	}
}
