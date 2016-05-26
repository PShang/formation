package fr.pizzeria.admin.metier;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections4.ListUtils;

import fr.pizzeria.dao.pizza.PizzaDaoFichierImpl;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Pizza;

@Stateless
public class PizzaService {
	@PersistenceContext(unitName = "pizzeria-admin-app") private EntityManager em;
	private static final String PIZZA_FIND_BY_CODE = "pizza.findByCode";

	public List<Pizza> findAllPizzas() {
		return em.createQuery("SELECT p FROM Pizza p", Pizza.class).getResultList();
	}

	public Pizza getPizza(String code) throws DaoException {
		return em.createNamedQuery(PIZZA_FIND_BY_CODE, Pizza.class).setParameter("code", code).getSingleResult();
	}

	public void saveNewPizza(Pizza pizza) throws DaoException {
		em.persist(pizza);
	}

	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		Pizza p = em.createNamedQuery(PIZZA_FIND_BY_CODE, Pizza.class).setParameter("code", codePizza).getSingleResult();
		p.setCode(codePizza);
		p.setNom(pizza.getNom());
		p.setPrix(pizza.getPrix());
		p.setCategorie(pizza.getCategorie());
	}

	public void deletePizza(String codePizza) throws DaoException {
		em.remove(em.createNamedQuery(PIZZA_FIND_BY_CODE, Pizza.class).setParameter("code", codePizza).getSingleResult());
	}

	public void importFromFiles(PizzaDaoFichierImpl pizzaDaoFichierImpl, int nb) throws DaoException {
		for (List<Pizza> list : ListUtils.partition(pizzaDaoFichierImpl.findAllPizzas(), nb)) {
			list.forEach(em::persist);
		}
	}
}
