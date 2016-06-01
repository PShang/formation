package fr.pizzeria.ihm.menu;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import fr.pizzeria.console.ConsoleLogger;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.ihm.menu.option.OptionMenu;

/**
 * Classe pour le menu principale.
 */
public class Menu {

	/**
	 * Constante pour le {@link titre} du {@link Menu}.
	 */
	private static final String MENU_TITRE_LIBELLE = "Pizzeria Client";
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
			ConsoleLogger.out(this.titre);
			actions.forEach((k, v) -> ConsoleLogger.out(k + ". " + v.getLibelle()));
			try {
				int choix = this.scan.nextInt();
				if (actions.containsKey(choix)) {
					stop = actions.get(choix).execute();
				} else {
					ConsoleLogger.err("Erreur : L'option " + choix + " n'existe pas.");
				}
			} catch (DaoException e) {
				ConsoleLogger.err(e.getMessage(), e);
			} catch (InputMismatchException e) {
				ConsoleLogger.err("Erreur de saisie : \"" + scan.next() + "\" n'était pas attendu.", e);
			}
		}
	}
}
