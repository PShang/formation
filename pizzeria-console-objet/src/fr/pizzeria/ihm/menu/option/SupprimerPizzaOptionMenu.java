package fr.pizzeria.ihm.menu.option;

public class SupprimerPizzaOptionMenu extends OptionMenu {

	private static final String SUPPRIMER_PIZZA_LIBELLE = "Supprimer une pizza";

	public SupprimerPizzaOptionMenu() {
		super(SUPPRIMER_PIZZA_LIBELLE);
	}

	@Override
	public boolean execute() {
		System.out.println("Supprimer Pizza Menu");// TODO: a remplir
		return false;
	}

}
