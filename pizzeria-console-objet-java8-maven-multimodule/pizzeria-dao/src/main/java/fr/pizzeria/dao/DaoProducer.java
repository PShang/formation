package fr.pizzeria.dao;

import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;

import fr.pizzeria.dao.client.ClientDaoJpaImpl;
import fr.pizzeria.dao.client.ClientDaoRestImpl;
import fr.pizzeria.dao.commande.CommandeDaoJpaImpl;
import fr.pizzeria.dao.pizza.PizzaDaoFichierImpl;
import fr.pizzeria.dao.pizza.PizzaDaoImpl;
import fr.pizzeria.dao.pizza.PizzaDaoJdbcImpl;
import fr.pizzeria.dao.pizza.PizzaDaoJpaImpl;
import fr.pizzeria.dao.pizza.PizzaDaoRestImpl;
import fr.pizzeria.exception.DaoException;

public class DaoProducer {

	public IDaoFactory getDaoFactoryJpa(EntityManagerFactory emf) {
		return new GenericDaoFactory(new PizzaDaoJpaImpl(emf), new ClientDaoJpaImpl(emf), new CommandeDaoJpaImpl(emf));
	}

	public IDaoFactory getDaoFactoryMemoire() {
		return new GenericDaoFactory(new PizzaDaoImpl(), null, null);
	}

	public IDaoFactory getDaoFactoryFichier() throws DaoException {
		return new GenericDaoFactory(new PizzaDaoFichierImpl(), null, null);
	}

	public IDaoFactory getDaoFactoryJdbc(String urlConnection, String userConnection, String passConnection) throws DaoException, SQLException {
		return new GenericDaoFactory(new PizzaDaoJdbcImpl(urlConnection, userConnection, passConnection), null, null);
	}

	public IDaoFactory getDaoFactoryRest(String baseUrl) {
		return new GenericDaoFactory(new PizzaDaoRestImpl(baseUrl), new ClientDaoRestImpl(baseUrl), null);
	}
}
