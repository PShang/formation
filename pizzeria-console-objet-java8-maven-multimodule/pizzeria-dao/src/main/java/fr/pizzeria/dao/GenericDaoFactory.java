package fr.pizzeria.dao;

import org.apache.commons.lang3.NotImplementedException;

import fr.pizzeria.dao.client.IClientDao;
import fr.pizzeria.dao.pizza.IPizzaDao;

public class GenericDaoFactory implements IDaoFactory {

	private IPizzaDao pizzaDao;
	private IClientDao clientDao;

	public GenericDaoFactory(IPizzaDao pizzaDao, IClientDao clientDao) {
		this.pizzaDao = pizzaDao;
		this.clientDao = clientDao;
	}

	private void check(Object dao) {
		if (dao == null) {
			throw new NotImplementedException("DAO non implémenté.");
		}
	}

	@Override
	public IPizzaDao getPizzaDao() {
		check(pizzaDao);
		return pizzaDao;
	}

	@Override
	public IClientDao getClientDao() {
		check(clientDao);
		return clientDao;
	}
}
