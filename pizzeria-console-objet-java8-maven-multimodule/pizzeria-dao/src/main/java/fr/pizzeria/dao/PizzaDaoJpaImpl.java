package fr.pizzeria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.apache.commons.collections4.ListUtils;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.Pizza;

/**
 * Implémentation de la DAO JPA pour les pizzas.
 */
public class PizzaDaoJpaImpl implements IPizzaDao {

	private EntityManagerFactory emf;

	/**
	 * Constructeur.
	 * 
	 * @param emf L'entityManagerFactory pour JPA.
	 */
	public PizzaDaoJpaImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public void close() {
		emf.close();
	}

	@Override
	public List<Pizza> findAllPizzas() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("SELECT p FROM Pizza p", Pizza.class).getResultList();
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws DaoException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(pizza);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new SavePizzaException("Erreur SQL lors de l'insertion des données.", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Pizza p = em.createNamedQuery("pizza.findByCode", Pizza.class).setParameter("code", codePizza)
					.getSingleResult();
			p.setCode(codePizza);
			p.setNom(pizza.getNom());
			p.setPrix(pizza.getPrix());
			p.setCategorie(pizza.getCategorie());
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new UpdatePizzaException("Erreur SQL lors de la mise à jour des données.", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void deletePizza(String codePizza) throws DaoException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.createNamedQuery("pizza.findByCode", Pizza.class).setParameter("code", codePizza)
					.getSingleResult());
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new DeletePizzaException("Erreur SQL lors de la suppression des données.", e);
		} finally {
			em.close();
		}
	}

	public void importFromFiles(PizzaDaoFichierImpl pizzaDaoFichierImpl) throws SavePizzaException {
		for (List<Pizza> list : ListUtils.partition(pizzaDaoFichierImpl.findAllPizzas(), 3)) {
			EntityManager em = emf.createEntityManager();
			try {
				em.getTransaction().begin();
				list.forEach(em::persist);
				em.getTransaction().commit();
			} catch (PersistenceException e) {
				em.getTransaction().rollback();
				em.close();
				throw new SavePizzaException("Erreur SQL lors de l'insertion des données.", e);
			} finally {
				em.close();
			}
		}
	}
}
