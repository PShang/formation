package fr.pizzeria.ihm.menu;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.ihm.menu.option.OptionMenu;

/**
 * Classe pour le menu principale.
 */
public class Menu {

	/**
	 * Constante pour le {@link titre} du {@link Menu}.
	 */
	private static final String MENU_TITRE_LIBELLE = "Pizzeria Administration";
	private String titre;
	private Scanner scan;
	private Map<Integer, OptionMenu> actions;

	/**
	 * Constructeur de {@link Menu}.
	 * 
	 * @param scan Un {@link Scanner} pour la gestion des saisies utilisateur.
	 * @param options La {@link Map} d'{@link OptionMenu}.
	 */
	public Menu(Scanner scan, Map<Integer, OptionMenu> options) {
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
			actions.forEach((k, v) -> System.out.println(k + ". " + v.getLibelle()));
			try {
				int choix = this.scan.nextInt();
				if (actions.containsKey(choix)) {
					try {
						stop = actions.get(choix).execute();
					} catch (DaoException e) {
						System.err.println(e.getMessage());
					}
				} else {
					System.err.println("Erreur : L'option " + choix + " n'existe pas.");
				}
			} catch (InputMismatchException e) {
				System.err.println("Erreur de saisie : \"" + scan.next() + "\" n'était pas attendu.");
			}
		}
		scan.close();
	}
}
