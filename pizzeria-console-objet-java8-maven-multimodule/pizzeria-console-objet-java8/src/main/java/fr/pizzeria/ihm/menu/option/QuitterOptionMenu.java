package fr.pizzeria.ihm.menu.option;

import fr.pizzeria.console.ConsoleLogger;

public class QuitterOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String QUITTER_LIBELLE = "Quitter";

	/**
	 * Constructeur.
	 */
	public QuitterOptionMenu() {
		super(QUITTER_LIBELLE);
	}

	@Override
	public boolean execute() {
		ConsoleLogger.out("Au revoir ;-)");
		return true;
	}
}
