package fr.pizzeria.dao.commande;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Commande;

public class CommandeDaoJpaImpl implements ICommandeDao {

	private EntityManagerFactory emf;

	public CommandeDaoJpaImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public void saveNewCommande(Commande commande) throws DaoException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(commande);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new DaoException("Erreur SQL lors de l'insertion de la commande.", e);
		} finally {
			em.close();
		}
	}
}
