package fr.pizzeria.ihm.menu.option;

public class QuitterOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de
	 * l'{@link OptionMenu}.
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
		System.out.println("Au revoir ;-)");
		return true;
	}
}
