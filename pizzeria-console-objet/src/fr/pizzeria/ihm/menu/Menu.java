package fr.pizzeria.ihm.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.ihm.menu.option.OptionMenu;

public class Menu {

	private static final String MENU_TITRE_LIBELLE = "Pizzeria Administration";
	private String titre;
	private Scanner scan;
	private OptionMenu[] actions;

	public Menu(Scanner scan, OptionMenu... options) {
		this.titre = "***** " + MENU_TITRE_LIBELLE + " *****";
		this.scan = scan;
		this.actions = options;
	}

	/**
	 * Affiche le Menu et demande les entrées utilisateur.
	 */
	public void afficher() {
		boolean stop = false;
		while (!stop) {
			System.out.println(this.titre);
			for (int i = 0; i < actions.length; ++i) {
				System.out.println(i + ". " + actions[i].getLibelle());
			}
			try {
				int choix = this.scan.nextInt();
				if (choix < this.actions.length) {
					try {
						stop = this.actions[choix].execute();
					} catch (DaoException e) {
						System.err.println(e.getMessage());
					}
				} else {
					System.err.println("Erreur : L'option " + choix + " n'existe pas.");
				}
			} catch (InputMismatchException e) {
				System.err.println("Erreur d'entrée.");
				scan.next();
			}
		}
		scan.close();
	}
}
