package fr.pizzeria.ihm.menu.option;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.model.Pizza;

public class AjouterPizzaOptionMenu extends OptionMenu {

	private static final String AJOUTER_PIZZA_LIBELLE = "Ajouter une pizza";
	private Scanner scan;

	public AjouterPizzaOptionMenu(IPizzaDao pizzaDao, Scanner scan) {
		super(AJOUTER_PIZZA_LIBELLE, pizzaDao);
		this.scan = scan;
	}

	@Override
	public boolean execute() {
		System.out.println("Ajout d’une nouvelle pizza");
		System.out.println("Veuillez saisir le code");
		String code = scan.next();
		System.out.println("Veuillez saisir le nom (sans espace)");
		String name = scan.next();
		System.out.println("Veuillez saisir le prix");
		double price = scan.nextDouble();
		if (!pizzaDao.saveNewPizza(new Pizza(code, name, price))) {
			System.err.println("Erreur : La pizza avec le code " + code + " existe déjà.");
		}
		System.out.println();
		return false;
	}
}
