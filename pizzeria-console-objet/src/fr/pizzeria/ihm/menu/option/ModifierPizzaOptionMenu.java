package fr.pizzeria.ihm.menu.option;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.Pizza;

public class ModifierPizzaOptionMenu extends OptionMenu {

	private static final String MODIFIER_PIZZA_LIBELLE = "Modifier une pizza";
	private Scanner scan;

	public ModifierPizzaOptionMenu(IPizzaDao pizzaDao, Scanner scan) {
		super(MODIFIER_PIZZA_LIBELLE, pizzaDao);
		this.scan = scan;
	}

	@Override
	public boolean execute() throws DaoException {
		System.out.println("Mise à jour d’une pizza");
		new ListerPizzaOptionMenu(pizzaDao).execute();
		System.out.println("Veuillez choisir le code la pizza à modifier.");
		System.out.println("(99 pour abandonner).");
		String code = scan.next();
		if (code.equals("99")) {
			return false;
		}
		System.out.println("Veuillez saisir le nom (sans espace)");
		String name = scan.next();
		System.out.println("Veuillez saisir le prix");
		double price = scan.nextDouble();
		try {
			pizzaDao.updatePizza(code, new Pizza(code, name, price));
		} catch (UpdatePizzaException e) {
			throw new DaoException("Erreur : La pizza avec le code " + code + " n'existe pas.", e);
		}
		System.out.println();
		return false;
	}
}
