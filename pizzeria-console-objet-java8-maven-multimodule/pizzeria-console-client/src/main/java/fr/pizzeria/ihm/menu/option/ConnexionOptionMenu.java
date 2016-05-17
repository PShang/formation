package fr.pizzeria.ihm.menu.option;

import fr.pizzeria.dao.IPizzaDao;

public class ConnexionOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de
	 * l'{@link OptionMenu}.
	 */
	private static final String CONNEXION_LIBELLE = "Se connecter";

	/**
	 * Constructeur.
	 * 
	 * @param pizzaDao La DAO pour les pizzas.
	 */
	public ConnexionOptionMenu(IPizzaDao pizzaDao) {
		super(CONNEXION_LIBELLE, pizzaDao);
	}

	@Override
	public boolean execute() {

		return false;
	}
}
