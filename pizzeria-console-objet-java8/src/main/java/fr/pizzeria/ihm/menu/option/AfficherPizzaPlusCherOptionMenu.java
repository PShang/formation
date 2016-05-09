package fr.pizzeria.ihm.menu.option;

import java.util.Comparator;
import java.util.stream.Collectors;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.model.Pizza;

public class AfficherPizzaPlusCherOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de
	 * l'{@link OptionMenu}.
	 */
	private static final String LISTER_PIZZAS_LIBELLE = "Afficher la pizza au tarif le plus élevé";

	/**
	 * Constructeur.
	 * 
	 * @param pizzaDao La DAO pour les pizzas.
	 */
	public AfficherPizzaPlusCherOptionMenu(IPizzaDao pizzaDao) {
		super(LISTER_PIZZAS_LIBELLE, pizzaDao);
	}

	@Override
	public boolean execute() {
		System.out.println("La pizza la plus cher : ");
		System.out.println(this.pizzaDao.findAllPizzas().stream()
				.collect(Collectors.maxBy(Comparator.comparing(Pizza::getPrix))).get());
		System.out.println("------- " + Pizza.nbPizzas + " pizzas créées depuis l’initialisation du programme");
		System.out.println();
		return false;
	}
}
