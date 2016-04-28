package fr.pizzeria.ihm.menu.option;

public class QuitterOptionMenu extends OptionMenu {

	private static final String QUITTER_LIBELLE = "Quitter";

	public QuitterOptionMenu() {
		super(QUITTER_LIBELLE);
	}

	@Override
	public boolean execute() {
		System.out.println("Au revoir ;-)");
		return true;
	}
}
