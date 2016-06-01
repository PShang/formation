package fr.pizzeria.dao.client;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.codec.digest.DigestUtils;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.SaveClientException;
import fr.pizzeria.model.Client;

public class ClientDaoJpaImpl implements IClientDao {
	private static final String NON_IMPLEMENTE_JPA = "La méthode n'est pas implémenté en JPA.";

	private EntityManagerFactory emf;

	public ClientDaoJpaImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public List<Client> findAllClients() {
		throw new UnsupportedOperationException(NON_IMPLEMENTE_JPA);
	}

	@Override
	public Client getClient(String email, String mdp) throws DaoException {
		EntityManager em = emf.createEntityManager();
		Client c = null;
		try {
			em.getTransaction().begin();
			c = em.createNamedQuery("client.findByEmail", Client.class).setParameter("email", email).getSingleResult();
			if (!DigestUtils.md5Hex(mdp).equals(c.getMdp())) {
				throw new DaoException("Le mot de passe est incorrect.", null);
			}
			em.getTransaction().commit();
		} catch (NoResultException e) {
			em.getTransaction().rollback();
			throw new DaoException("Le client avec cette adresse email n'existe pas.", e);
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new SaveClientException("Erreur SQL lors de l'insertion des données.", e);
		} finally {
			em.close();
		}
		return c;
	}

	@Override
	public void saveNewClient(Client client) throws DaoException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(client);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new SaveClientException("Erreur SQL lors de l'insertion du client.", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void updateClient(Integer idClient, Client client) throws DaoException {
		throw new UnsupportedOperationException(NON_IMPLEMENTE_JPA);
	}

	@Override
	public void deleteClient(Integer idClient) throws DaoException {
		throw new UnsupportedOperationException(NON_IMPLEMENTE_JPA);
	}
}
