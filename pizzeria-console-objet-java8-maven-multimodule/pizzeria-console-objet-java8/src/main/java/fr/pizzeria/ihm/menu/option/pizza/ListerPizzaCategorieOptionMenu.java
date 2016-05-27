package fr.pizzeria.ihm.menu.option.pizza;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.ihm.menu.option.OptionMenu;
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
	 * @param dao La DAO Factory.
	 */
	public ListerPizzaCategorieOptionMenu(IDaoFactory dao) {
		super(LISTER_PIZZAS_LIBELLE, dao);
	}

	@Override
	public boolean execute() {
		Map<CategoriePizza, List<Pizza>> map = dao.getPizzaDao().findAllPizzas().stream().collect(Collectors.groupingBy(Pizza::getCategorie));
		map.forEach((k, v) -> {
			System.out.println("--- " + k.getLibelle() + " :");
			v.stream().forEach(System.out::println);
		});
		System.out.println("------- " + Pizza.nbPizzas + " pizzas créées depuis l'initialisation du programme");
		System.out.println();
		return false;
	}
}
