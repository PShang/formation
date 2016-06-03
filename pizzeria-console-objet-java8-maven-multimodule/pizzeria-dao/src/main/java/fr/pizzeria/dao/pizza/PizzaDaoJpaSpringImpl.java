package fr.pizzeria.dao.pizza;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Pizza;

/**
 * Implémentation de la DAO JPA Spring pour les pizzas.
 */
@Repository
@Lazy
public class PizzaDaoJpaSpringImpl implements IPizzaDao {

	private static final String PIZZA_FIND_BY_CODE = "pizza.findByCode";
	@PersistenceContext private EntityManager em;
	@Autowired private BatchPizzaDaoJpaSpring batchPizzaDaoJpaSpring;

	public PizzaDaoJpaSpringImpl() {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Création du bean " + this.getClass().getName());
	}

	@Override
	public List<Pizza> findAllPizzas() {
		return em.createQuery("SELECT p FROM Pizza p", Pizza.class).getResultList();
	}

	@Override
	public Pizza getPizza(String code) throws DaoException {
		Pizza p;
		try {
			p = em.createNamedQuery(PIZZA_FIND_BY_CODE, Pizza.class).setParameter("code", code).getSingleResult();
		} catch (NoResultException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "La pizza avec le code " + code + " n'existe pas.", e);
			p = null;
		}
		return p;
	}

	@Override
	@Transactional
	public void saveNewPizza(Pizza pizza) throws DaoException {
		em.persist(pizza);
	}

	@Override
	@Transactional
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		Pizza p = em.createNamedQuery(PIZZA_FIND_BY_CODE, Pizza.class).setParameter("code", codePizza).getSingleResult();
		p.setCode(codePizza);
		p.setNom(pizza.getNom());
		p.setPrix(pizza.getPrix());
		p.setCategorie(pizza.getCategorie());
	}

	@Override
	@Transactional
	public void deletePizza(String codePizza) throws DaoException {
		em.remove(em.createNamedQuery(PIZZA_FIND_BY_CODE, Pizza.class).setParameter("code", codePizza).getSingleResult());
	}

	@Override
	@Transactional
	public void saveAllPizzas(List<Pizza> pizzas, int nb) throws DaoException {
		ListUtils.partition(pizzas, nb).forEach(batchPizzaDaoJpaSpring::save);
	}
}
