package fr.pizzeria.dao.pizza;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.repos.IPizzaRepository;

/**
 * Implémentation de la DAO JPA Data pour les pizzas.
 */
@Repository
@Lazy
public class PizzaDaoJpaDataImpl implements IPizzaDao {

	@Autowired private IPizzaRepository pizzaRepository;
	@Autowired private BatchPizzaDaoJpaData batchPizzaDaoJpaData;

	public PizzaDaoJpaDataImpl() {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "===== Création du bean " + this.getClass().getName() + " =====");
	}

	@Override
	public List<Pizza> findAllPizzas() {
		return pizzaRepository.findAll();
	}

	@Override
	public Pizza getPizza(String code) throws DaoException {
		return pizzaRepository.findOneByCode(code);
	}

	@Override
	@Transactional
	public void saveNewPizza(Pizza pizza) throws DaoException {
		pizzaRepository.save(pizza);
	}

	@Override
	@Transactional
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		Pizza p = pizzaRepository.findOneByCode(codePizza);
		if (p == null) {
			throw new UpdatePizzaException();
		}
		pizza.setId(p.getId());
		pizzaRepository.save(pizza);
	}

	@Override
	@Transactional
	public void deletePizza(String codePizza) throws DaoException {
		if (0 == pizzaRepository.deleteByCode(codePizza)) {
			throw new DeletePizzaException();
		}
	}

	@Override
	@Transactional
	public void saveAllPizzas(List<Pizza> pizzas, int nb) throws DaoException {
		ListUtils.partition(pizzas, nb).forEach(batchPizzaDaoJpaData::save);
	}
}
