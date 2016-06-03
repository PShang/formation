package fr.pizzeria.dao.commande;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Commande;

@Repository
@Lazy
public class CommandeDaoJpaImpl implements ICommandeDao {

	private EntityManagerFactory emf;

	@Autowired
	public CommandeDaoJpaImpl(EntityManagerFactory emf) {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Création du bean " + this.getClass().getName());
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
