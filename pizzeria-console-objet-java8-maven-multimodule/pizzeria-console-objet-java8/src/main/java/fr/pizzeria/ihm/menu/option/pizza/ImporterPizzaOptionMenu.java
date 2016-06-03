package fr.pizzeria.ihm.menu.option.pizza;

import java.util.List;
import java.util.Scanner;

import fr.pizzeria.console.ConsoleLogger;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.dao.pizza.IPizzaDao;
import fr.pizzeria.dao.pizza.PizzaDaoFichierImpl;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.model.Pizza;

public class ImporterPizzaOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String IMPORTER_PIZZA_LIBELLE = "(Base de données) Importer les données";

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO Factory.
	 * @param scan Le {@link Scanner} pour la saisie utilisateur.
	 */
	public ImporterPizzaOptionMenu(IDaoFactory dao) {
		super(IMPORTER_PIZZA_LIBELLE, dao);
	}

	@Override
	public boolean execute() throws DaoException {
		IPizzaDao pizzaDao = dao.getPizzaDao();
		List<Pizza> pizzas = new PizzaDaoFichierImpl().findAllPizzas();
		pizzaDao.saveAllPizzas(pizzas, 3);
		ConsoleLogger.out("");
		return false;
	}
}
