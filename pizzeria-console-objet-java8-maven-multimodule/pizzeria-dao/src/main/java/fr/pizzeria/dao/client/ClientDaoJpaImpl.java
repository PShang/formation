package fr.pizzeria.dao.client;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.codec.digest.DigestUtils;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.SaveClientException;
import fr.pizzeria.model.Client;
import fr.pizzeria.model.Commande;

public class ClientDaoJpaImpl implements IClientDao {

	private EntityManagerFactory emf;

	public ClientDaoJpaImpl(EntityManagerFactory emf) {
		this.emf = emf;
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
	public Set<Commande> getAllCommandes(Client client) throws DaoException {
		EntityManager em = emf.createEntityManager();
		Set<Commande> commandes = null;
		try {
			em.getTransaction().begin();
			Client c = em.find(Client.class, client.getId());
			commandes = c.getCommandes();
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new DaoException("Erreur SQL lors de la lecture des données.", e);
		} finally {
			em.close();
		}
		return commandes;
	}
}
