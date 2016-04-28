package fr.pizzeria.ihm.menu.option;

public class AjouterPizzaOptionMenu extends OptionMenu {

	private static final String AJOUTER_PIZZA_LIBELLE = "Ajouter une pizza";

	public AjouterPizzaOptionMenu() {
		super(AJOUTER_PIZZA_LIBELLE);
	}

	@Override
	public boolean execute() {
		System.out.println("Ajouter Pizza Menu");// TODO: a remplir
		return false;
	}

}
