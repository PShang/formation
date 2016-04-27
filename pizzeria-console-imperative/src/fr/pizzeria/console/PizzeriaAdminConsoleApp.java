package fr.pizzeria.console;

import java.util.Scanner;

public class PizzeriaAdminConsoleApp {

	static Object[][] pizzas = { { 0, "PEP", "Pépéroni", 12.50 }, { 1, "MAR", "Margherita", 14.00 },
			{ 2, "REI", "La Reine", 11.50 }, { 3, "FRO", "La 4 fromages", 12.00 }, { 4, "CAN", "La cannibale", 12.50 },
			{ 5, "SAV", "La savoyarde", 13.00 }, { 6, "ORI", "L'orientale", 13.50 },
			{ 7, "IND", "L'indienne", 14.00 } };

	/**
	 * Affiche la liste des pizza.
	 */
	private static void showPizzas() {
		for (Object[] pizza : pizzas) {
			System.out.println(pizza[1] + " -> " + pizza[2] + " (" + pizza[3] + "€)");
		}
	}

	/**
	 * Ajouter une pizza.
	 * 
	 * @param code Le code de la nouvelle pizza.
	 * @param name Le nom de la nouvelle pizza.
	 * @param price Le prix de la nouvelle pizza.
	 */
	private static void addPizza(String code, String name, double price) {
		int nb_pizza = pizzas.length;
		Object[][] p = new Object[nb_pizza + 1][4];
		int i = 0;
		while (i < nb_pizza) {
			p[i] = pizzas[i++];
		}
		p[i] = new Object[] { i, code, name, price };
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
		for (int i = 0; i < pizzas.length; ++i) {
			if (pizzas[i][1].toString().equals(oldCode)) {
				pizzas[i] = new Object[] { i, code, name, price };
			}
		}
	}

	/**
	 * Supprimer une pizza.
	 * 
	 * @param oldCode Le code de la pizza à supprimer.
	 */
	private static void deletePizza(String oldCode) {
		int nb_pizza = pizzas.length;
		Object[][] p = new Object[nb_pizza - 1][4];
		int i = 0;
		int j = 0;
		while (i < nb_pizza) {
			if (!pizzas[i][1].toString().equals(oldCode)) {
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
		boolean stop = false;
		String code, name;
		double price;

		while (!stop) {
			System.out.println("***** Pizzeria Administration *****");
			System.out.println("1. Lister les pizzas");
			System.out.println("2. Ajouter une nouvelle pizza");
			System.out.println("3. Mettre à jour une pizza");
			System.out.println("4. Supprimer une pizza");
			System.out.println("99. Sortir");

			int choice = scan.nextInt();
			switch (choice) {
				case 1:
					System.out.println("Liste des pizzas");
					showPizzas();
					System.out.println();
					break;
				case 2:
					System.out.println("Ajout d’une nouvelle pizza");
					System.out.println("Veuillez saisir le code");
					code = scan.next();
					System.out.println("Veuillez saisir le nom (sans espace)");
					name = scan.next();
					System.out.println("Veuillez saisir le prix");
					price = scan.nextDouble();
					addPizza(code, name, price);
					System.out.println();
					break;
				case 3:
					System.out.println("Mise à jour d’une pizza");
					showPizzas();
					System.out.println("Veuillez choisir la pizza à modifier.");
					System.out.println("(99 pour abandonner).");
					String oldCode = scan.next();
					if (oldCode.equals("99")) {
						break;
					}
					System.out.println("Veuillez saisir le code");
					code = scan.next();
					System.out.println("Veuillez saisir le nom (sans espace)");
					name = scan.next();
					System.out.println("Veuillez saisir le prix");
					price = scan.nextDouble();
					editPizza(oldCode, code, name, price);
					System.out.println();
					break;
				case 4:
					System.out.println("Suppression d’une pizza");
					showPizzas();
					System.out.println("Veuillez choisir la pizza à modifier.");
					System.out.println("(99 pour abandonner).");
					oldCode = scan.next();
					if (oldCode.equals("99")) {
						break;
					}
					deletePizza(oldCode);
					System.out.println();
					break;
				case 99:
					System.out.println("Aurevoir");
					stop = true;
					break;
				default:
					System.out.println("Erreur : L'option " + choice + " n'existe pas.");
					System.out.println();
					break;
			}
		}
		scan.close();
	}
}
