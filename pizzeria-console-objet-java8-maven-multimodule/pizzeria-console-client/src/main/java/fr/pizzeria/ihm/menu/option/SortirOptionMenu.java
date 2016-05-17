package fr.pizzeria.ihm.menu.option;

public class SortirOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de
	 * l'{@link OptionMenu}.
	 */
	private static final String SORTIR_LIBELLE = "Sortir";

	/**
	 * Constructeur.
	 */
	public SortirOptionMenu() {
		super(SORTIR_LIBELLE);
	}

	@Override
	public boolean execute() {
		return true;
	}
}
