package fr.pizzeria.admin.metier;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import fr.pizzeria.dao.pizza.IPizzaDao;
import fr.pizzeria.dao.pizza.PizzaDaoImpl;

public class BeanProducer {

	private BeanProducer() {}

	@Produces
	@ApplicationScoped
	public static IPizzaDao getPizzaDao() {
		return new PizzaDaoImpl();
	}
}
