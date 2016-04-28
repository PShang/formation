package fr.pizzeria.ihm.menu.option;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;

public class SupprimerPizzaOptionMenu extends OptionMenu {

	private static final String SUPPRIMER_PIZZA_LIBELLE = "Supprimer une pizza";
	private Scanner scan;

	public SupprimerPizzaOptionMenu(IPizzaDao pizzaDao, Scanner scan) {
		super(SUPPRIMER_PIZZA_LIBELLE, pizzaDao);
		this.scan = scan;
	}

	@Override
	public boolean execute() {
		System.out.println("Suppression d’une pizza");
		new ListerPizzaOptionMenu(pizzaDao).execute();
		System.out.println("Veuillez choisir le code la pizza à modifier.");
		System.out.println("(99 pour abandonner).");
		String oldCode = scan.next();
		if (oldCode.equals("99")) {
			return false;
		}
		if (!pizzaDao.deletePizza(oldCode)) {
			System.err.println("Erreur : La pizza avec le code " + oldCode + " n'existe pas.");
		}
		System.out.println();
		return false;
	}
}
