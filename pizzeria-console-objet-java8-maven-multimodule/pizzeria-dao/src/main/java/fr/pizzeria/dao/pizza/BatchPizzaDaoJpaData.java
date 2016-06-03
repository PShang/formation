package fr.pizzeria.dao.pizza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.pizzeria.model.Pizza;
import fr.pizzeria.repos.IPizzaRepository;

@Repository
public class BatchPizzaDaoJpaData {

	@Autowired private IPizzaRepository pizzaRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void save(List<Pizza> list) {
		list.forEach(pizzaRepository::save);
	}
}