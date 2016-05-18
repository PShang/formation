package fr.pizzeria.ihm.menu.option;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.pizzeria.dao.pizza.IPizzaDao;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class ListerPizzaCategorieOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String LISTER_PIZZAS_LIBELLE = "Lister les pizzas groupées par catégorie";

	/**
	 * Constructeur.
	 * 
	 * @param pizzaDao La DAO pour les pizzas.
	 */
	public ListerPizzaCategorieOptionMenu(IPizzaDao pizzaDao) {
		super(LISTER_PIZZAS_LIBELLE, pizzaDao);
	}

	@Override
	public boolean execute() {
		Map<CategoriePizza, List<Pizza>> map = this.pizzaDao.findAllPizzas().stream()
				.collect(Collectors.groupingBy(Pizza::getCategorie));
		map.forEach((k, v) -> {
			System.out.println("--- " + k.getLibelle() + " :");
			v.stream().forEach(System.out::println);
		});
		System.out.println("------- " + Pizza.nbPizzas + " pizzas créées depuis l'initialisation du programme");
		System.out.println();
		return false;
	}
}
