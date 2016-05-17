package fr.pizzeria.ihm.menu.option;

import fr.pizzeria.dao.IPizzaDao;

public class InscriptionOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de
	 * l'{@link OptionMenu}.
	 */
	private static final String INSCRIPTION_LIBELLE = "S'inscrire";

	/**
	 * Constructeur.
	 * 
	 * @param pizzaDao La DAO pour les pizzas.
	 */
	public InscriptionOptionMenu(IPizzaDao pizzaDao) {
		super(INSCRIPTION_LIBELLE, pizzaDao);
	}

	@Override
	public boolean execute() {

		return false;
	}
}
