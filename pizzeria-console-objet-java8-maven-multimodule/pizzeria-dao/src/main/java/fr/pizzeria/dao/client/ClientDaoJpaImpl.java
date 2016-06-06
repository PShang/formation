package fr.pizzeria.dao.client;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SaveClientException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.Client;

@Repository
@Lazy
public class ClientDaoJpaImpl implements IClientDao {

	private EntityManagerFactory emf;

	@Autowired
	public ClientDaoJpaImpl(EntityManagerFactory emf) {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "===== Création du bean " + this.getClass().getName() + " =====");
		this.emf = emf;
	}

	@Override
	public List<Client> findAllClients() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
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
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Client p = em.find(Client.class, idClient);
			p.setNom(client.getNom());
			p.setPrenom(client.getPrenom());
			p.setEmail(client.getEmail());
			p.setMdp(client.getMdp());
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new UpdatePizzaException("Erreur SQL lors de la mise à jour des données.", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteClient(Integer idClient) throws DaoException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.find(Client.class, idClient));
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new DeletePizzaException("Erreur SQL lors de la suppression des données.", e);
		} finally {
			em.close();
		}
	}
}
