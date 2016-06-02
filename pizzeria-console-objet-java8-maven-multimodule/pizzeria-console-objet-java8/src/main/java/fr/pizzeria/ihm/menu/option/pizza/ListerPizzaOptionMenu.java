package fr.pizzeria.ihm.menu.option.pizza;

import fr.pizzeria.console.ConsoleLogger;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.model.Pizza;

public class ListerPizzaOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String LISTER_PIZZAS_LIBELLE = "Lister les pizzas";

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO Factory.
	 */
	public ListerPizzaOptionMenu(IDaoFactory dao) {
		super(LISTER_PIZZAS_LIBELLE, dao);
	}

	@Override
	public boolean execute() {
		dao.getPizzaDao().findAllPizzas().stream().forEach(ConsoleLogger::out);
		ConsoleLogger.out("------- " + Pizza.getNbPizzas() + " pizzas créées depuis l'initialisation du programme");
		ConsoleLogger.out("");
		return false;
	}
}
