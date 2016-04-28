package fr.pizzeria.ihm.menu.option;

public class ModifierPizzaOptionMenu extends OptionMenu {

	private static final String MODIFIER_PIZZA_LIBELLE = "Modifier une pizza";

	public ModifierPizzaOptionMenu() {
		super(MODIFIER_PIZZA_LIBELLE);
	}

	@Override
	public boolean execute() {
		System.out.println("Modifier Pizza Menu");// TODO: a remplir
		return false;
	}

}
