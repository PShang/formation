package fr.pizzeria.ihm.menu.option;

import java.util.List;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.model.Pizza;

public class ListerPizzaOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de
	 * l'{@link OptionMenu}.
	 */
	private static final String LISTER_PIZZAS_LIBELLE = "Lister les pizzas";

	/**
	 * Constructeur.
	 * 
	 * @param pizzaDao La DAO pour les pizzas.
	 */
	public ListerPizzaOptionMenu(IPizzaDao pizzaDao) {
		super(LISTER_PIZZAS_LIBELLE, pizzaDao);
	}

	@Override
	public boolean execute() {
		List<Pizza> pizzas = this.pizzaDao.findAllPizzas();
		for (Pizza p : pizzas) {
			System.out.println(p.toString());
		}
		System.out.println("------- " + Pizza.nbPizzas + " pizzas créées depuis l’initialisation du programme");
		System.out.println();
		return false;
	}
}
