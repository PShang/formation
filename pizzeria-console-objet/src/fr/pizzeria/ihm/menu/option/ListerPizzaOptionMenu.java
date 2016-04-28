package fr.pizzeria.ihm.menu.option;

public class ListerPizzaOptionMenu extends OptionMenu {

	private static final String LISTER_PIZZAS_LIBELLE = "Lister les pizzas";

	public ListerPizzaOptionMenu() {
		super(LISTER_PIZZAS_LIBELLE);
	}

	@Override
	public boolean execute() {
		System.out.println("Lister Pizza Menu");// TODO: a remplir
		return false;
	}

}
