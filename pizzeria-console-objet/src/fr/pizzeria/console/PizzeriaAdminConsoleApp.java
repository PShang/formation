package fr.pizzeria.console;

import java.util.Scanner;

import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.ihm.menu.option.AjouterPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.ListerPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.ModifierPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.QuitterOptionMenu;
import fr.pizzeria.ihm.menu.option.SupprimerPizzaOptionMenu;
import fr.pizzeria.model.Pizza;

public class PizzeriaAdminConsoleApp {

	static Pizza[] pizzas;

	/**
	 * Crée un objet Pizza
	 * 
	 * @param id L'id de la pizza.
	 * @param code Le code de la pizza.
	 * @param nom Le nom de la pizza.
	 * @param prix Le prix de la pizza.
	 * @return
	 */
	private static Pizza createPizza(int id, String code, String nom, double prix) {
		Pizza p = new Pizza();
		p.id = id;
		p.code = code;
		p.nom = nom;
		p.prix = prix;
		Pizza.nbPizzas++;
		return (p);
	}

	/**
	 * Initialise les pizzas
	 */
	private static void initPizzas() {
		pizzas = new Pizza[8];
		pizzas[0] = createPizza(0, "PEP", "Pépéroni", 12.50);
		pizzas[1] = createPizza(1, "MAR", "Margherita", 14.00);
		pizzas[2] = createPizza(2, "REI", "La Reine", 11.50);
		pizzas[3] = createPizza(3, "FRO", "La 4 fromages", 12.00);
		pizzas[5] = createPizza(4, "CAN", "La cannibale", 12.50);
		pizzas[4] = createPizza(5, "SAV", "La savoyarde", 13.00);
		pizzas[6] = createPizza(6, "ORI", "L'orientale", 13.50);
		pizzas[7] = createPizza(7, "IND", "L'indienne", 14.00);
	}

	/**
	 * Vérifie si la pizza existe.
	 * 
	 * @param code Le code de la pizza à chercher.
	 * @return {@code true} si la pizza existe.
	 */
	private static boolean pizzaExists(String code) {
		for (Pizza pizza : pizzas) {
			if (pizza.code.equals(code))
				return true;
		}
		return false;
	}

	/**
	 * Affiche la liste des pizza.
	 */
	private static void showPizzas() {
		for (Pizza pizza : pizzas) {
			System.out.println(pizza.code + " -> " + pizza.nom + " (" + pizza.prix + "€)");
		}
		System.out.println("------- " + Pizza.nbPizzas + " pizzas créées depuis l’initialisation du programme");
	}

	/**
	 * Ajouter une pizza.
	 * 
	 * @param code Le code de la nouvelle pizza.
	 * @param name Le nom de la nouvelle pizza.
	 * @param price Le prix de la nouvelle pizza.
	 */
	private static void addPizza(String code, String name, double price) {
		if (pizzaExists(code)) {
			System.err.println("Erreur : La pizza avec le code " + code + " existe déjà.");
			return;
		}
		int nb_pizza = pizzas.length;
		Pizza[] p = new Pizza[nb_pizza + 1];
		int i = 0;
		while (i < nb_pizza) {
			p[i] = pizzas[i++];
		}
		p[i] = createPizza(i, code, name, price);
		pizzas = p;
	}

	/**
	 * Modifier une pizza.
	 * 
	 * @param oldCode Le code de la pizza à modifier.
	 * @param code Le nouveau code de la pizza à modifier.
	 * @param name Le nouveau nom de la pizza à modifier.
	 * @param price Le nouveau prix de la pizza à modifier.
	 */
	private static void editPizza(String oldCode, String code, String name, double price) {
		if (!pizzaExists(oldCode)) {
			System.err.println("Erreur : La pizza avec le code " + oldCode + " n'existe pas.");
			return;
		}
		for (int i = 0; i < pizzas.length; ++i) {
			if (pizzas[i].code.equals(oldCode)) {
				pizzas[i] = createPizza(i, code, name, price);
			}
		}
	}

	/**
	 * Supprimer une pizza.
	 * 
	 * @param oldCode Le code de la pizza à supprimer.
	 */
	private static void deletePizza(String oldCode) {
		if (!pizzaExists(oldCode)) {
			System.err.println("Erreur : La pizza avec le code " + oldCode + " n'existe pas.");
			return;
		}
		int nb_pizza = pizzas.length;
		Pizza[] p = new Pizza[nb_pizza - 1];
		int i = 0;
		int j = 0;
		while (i < nb_pizza) {
			if (!pizzas[i].code.equals(oldCode)) {
				p[i - j] = pizzas[i];
			} else {
				++j;
			}
			++i;
		}
		pizzas = p;
	}

	/**
	 * Methode d'entrée. Boucle pour afficher le menu et demander les options.
	 * 
	 * @param args Les aguments du programme.
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Menu menu = new Menu(scan, new ListerPizzaOptionMenu(), new AjouterPizzaOptionMenu(),
				new ModifierPizzaOptionMenu(), new SupprimerPizzaOptionMenu(), new QuitterOptionMenu());
		menu.afficher();

		/*
		 * initPizzas(); Scanner scan = new Scanner(System.in); boolean stop =
		 * false; String code, name; double price;
		 * 
		 * while (!stop) {
		 * System.out.println("***** Pizzeria Administration *****");
		 * System.out.println("1. Lister les pizzas");
		 * System.out.println("2. Ajouter une nouvelle pizza");
		 * System.out.println("3. Mettre à jour une pizza");
		 * System.out.println("4. Supprimer une pizza");
		 * System.out.println("99. Sortir");
		 * 
		 * int choice = scan.nextInt(); switch (choice) { case 1:
		 * System.out.println("Liste des pizzas"); showPizzas();
		 * System.out.println(); break; case 2:
		 * System.out.println("Ajout d’une nouvelle pizza");
		 * System.out.println("Veuillez saisir le code"); code = scan.next();
		 * System.out.println("Veuillez saisir le nom (sans espace)"); name =
		 * scan.next(); System.out.println("Veuillez saisir le prix"); price =
		 * scan.nextDouble(); addPizza(code, name, price); System.out.println();
		 * break; case 3: System.out.println("Mise à jour d’une pizza");
		 * showPizzas();
		 * System.out.println("Veuillez choisir la pizza à modifier.");
		 * System.out.println("(99 pour abandonner)."); String oldCode =
		 * scan.next(); if (oldCode.equals("99")) { break; }
		 * System.out.println("Veuillez saisir le code"); code = scan.next();
		 * System.out.println("Veuillez saisir le nom (sans espace)"); name =
		 * scan.next(); System.out.println("Veuillez saisir le prix"); price =
		 * scan.nextDouble(); editPizza(oldCode, code, name, price);
		 * System.out.println(); break; case 4:
		 * System.out.println("Suppression d’une pizza"); showPizzas();
		 * System.out.println("Veuillez choisir la pizza à modifier.");
		 * System.out.println("(99 pour abandonner)."); oldCode = scan.next();
		 * if (oldCode.equals("99")) { break; } deletePizza(oldCode);
		 * System.out.println(); break; case 99: System.out.println("Aurevoir");
		 * stop = true; break; default: System.err.println("Erreur : L'option "
		 * + choice + " n'existe pas."); System.out.println(); break; } }
		 * scan.close();
		 */
	}
}
