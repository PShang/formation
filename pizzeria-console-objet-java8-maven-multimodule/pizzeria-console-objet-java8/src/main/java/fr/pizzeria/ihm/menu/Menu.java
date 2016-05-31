package fr.pizzeria.ihm.menu;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.ihm.menu.option.QuitterOptionMenu;
import fr.pizzeria.ihm.menu.option.client.AjouterClientOptionMenu;
import fr.pizzeria.ihm.menu.option.client.ListerClientOptionMenu;
import fr.pizzeria.ihm.menu.option.client.ModifierClientOptionMenu;
import fr.pizzeria.ihm.menu.option.client.SupprimerClientOptionMenu;
import fr.pizzeria.ihm.menu.option.pizza.AfficherPizzaPlusCherOptionMenu;
import fr.pizzeria.ihm.menu.option.pizza.AjouterPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.pizza.ImporterPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.pizza.ListerPizzaCategorieOptionMenu;
import fr.pizzeria.ihm.menu.option.pizza.ListerPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.pizza.ModifierPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.pizza.SupprimerPizzaOptionMenu;

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
	private IDaoFactory dao;
	private Map<Integer, OptionMenu> options;

	/**
	 * Constructeur de {@link Menu}.
	 * 
	 * @param scan Un {@link Scanner} pour la gestion des saisies utilisateur.
	 * @param dao La DAO Factory.
	 */
	public Menu(Scanner scan, IDaoFactory dao) {
		this.titre = "***** " + MENU_TITRE_LIBELLE + " *****";
		this.scan = scan;
		this.dao = dao;
		initOptions();
	}

	/**
	 * Initialise les {@link OptionMenu}.
	 */
	private void initOptions() {
		options = new TreeMap<>();
		options.put(0, new ListerPizzaOptionMenu(dao));
		options.put(1, new AjouterPizzaOptionMenu(dao, scan));
		options.put(2, new ModifierPizzaOptionMenu(dao, scan));
		options.put(3, new SupprimerPizzaOptionMenu(dao, scan));
		options.put(4, new ListerPizzaCategorieOptionMenu(dao));
		options.put(5, new AfficherPizzaPlusCherOptionMenu(dao));
		options.put(6, new ImporterPizzaOptionMenu(dao));
		options.put(7, new ListerClientOptionMenu(dao));
		options.put(8, new AjouterClientOptionMenu(dao, scan));
		options.put(9, new ModifierClientOptionMenu(dao, scan));
		options.put(10, new SupprimerClientOptionMenu(dao, scan));
		options.put(99, new QuitterOptionMenu());
	}

	/**
	 * Affiche le Menu et demande les entrées utilisateur.
	 */
	public void afficher() {
		boolean stop = false;
		while (!stop) {
			System.out.println(this.titre);
			options.forEach((k, v) -> System.out.println(k + ". " + v.getLibelle()));
			try {
				int choix = this.scan.nextInt();
				if (options.containsKey(choix)) {
					stop = options.get(choix).execute();
				} else {
					System.err.println("Erreur : L'option " + choix + " n'existe pas.");
				}
			} catch (DaoException e) {
				System.err.println(e.getMessage());
			} catch (InputMismatchException e) {
				System.err.println("Erreur de saisie : \"" + scan.next() + "\" n'était pas attendu.");
			}
		}
	}
}
