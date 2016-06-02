package fr.pizzeria.ihm.menu.option.pizza;

import java.util.Comparator;
import java.util.stream.Collectors;

import fr.pizzeria.console.ConsoleLogger;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.model.Pizza;

public class AfficherPizzaPlusCherOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String LISTER_PIZZAS_LIBELLE = "Afficher la pizza au tarif le plus élevé";

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO Factory.
	 */
	public AfficherPizzaPlusCherOptionMenu(IDaoFactory dao) {
		super(LISTER_PIZZAS_LIBELLE, dao);
	}

	@Override
	public boolean execute() {
		ConsoleLogger.out("La pizza la plus cher : ");
		ConsoleLogger.out(dao.getPizzaDao().findAllPizzas().stream().collect(Collectors.maxBy(Comparator.comparing(Pizza::getPrix))).get());
		ConsoleLogger.out("------- " + Pizza.getNbPizzas() + " pizzas créées depuis l'initialisation du programme");
		ConsoleLogger.out("");
		return false;
	}
}
