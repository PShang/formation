package fr.pizzeria.dao.pizza;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.Pizza;

/**
 * Implémentation de la DAO JPA pour les pizzas.
 */
@Repository
@Lazy
public class PizzaDaoJpaImpl implements IPizzaDao {

	private static final String PIZZA_FIND_BY_CODE = "pizza.findByCode";
	private EntityManagerFactory emf;

	/**
	 * Constructeur.
	 * 
	 * @param emf L'entityManagerFactory pour JPA.
	 */
	@Autowired
	public PizzaDaoJpaImpl(EntityManagerFactory emf) {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Création du bean " + this.getClass().getName());
		this.emf = emf;
	}

	@Override
	public List<Pizza> findAllPizzas() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("SELECT p FROM Pizza p", Pizza.class).getResultList();
	}

	@Override
	public Pizza getPizza(String code) throws DaoException {
		Pizza p = null;
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			p = em.createNamedQuery(PIZZA_FIND_BY_CODE, Pizza.class).setParameter("code", code).getSingleResult();
			em.getTransaction().commit();
		} catch (NoResultException e) {
			em.getTransaction().rollback();
			throw new DaoException("La pizza n'existe pas.", e);
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new UpdatePizzaException("Erreur SQL lors de la mise à jour des données.", e);
		} finally {
			em.close();
		}
		return p;
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
			Pizza p = em.createNamedQuery(PIZZA_FIND_BY_CODE, Pizza.class).setParameter("code", codePizza).getSingleResult();
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
			em.remove(em.createNamedQuery(PIZZA_FIND_BY_CODE, Pizza.class).setParameter("code", codePizza).getSingleResult());
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new DeletePizzaException("Erreur SQL lors de la suppression des données.", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void saveAllPizzas(List<Pizza> pizzas, int nb) throws DaoException {
		for (List<Pizza> list : ListUtils.partition(pizzas, nb)) {
			EntityManager em = emf.createEntityManager();
			try {
				em.getTransaction().begin();
				list.forEach(em::persist);
				em.getTransaction().commit();
			} catch (PersistenceException e) {
				em.getTransaction().rollback();
				throw new SavePizzaException("Erreur SQL lors de l'insertion des données.", e);
			} finally {
				em.close();
			}
		}
	}
}
