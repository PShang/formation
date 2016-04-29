package fr.pizzeria.console;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaDaoImpl;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.ihm.menu.option.AjouterPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.ListerPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.ModifierPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.ihm.menu.option.QuitterOptionMenu;
import fr.pizzeria.ihm.menu.option.SupprimerPizzaOptionMenu;

/**
 * Classe principale de l'application.
 */
public class PizzeriaAdminConsoleApp {

	/**
	 * Methode d'entrée. Initialise le {@link Menu}, le {@link Scanner} et la
	 * {@link PizzaDaoImpl DAO}.
	 * 
	 * @param args Les aguments du programme.
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		IPizzaDao pizzaDao = new PizzaDaoImpl();

		Map<Integer, OptionMenu> options = new TreeMap<Integer, OptionMenu>();
		options.put(0, new ListerPizzaOptionMenu(pizzaDao));
		options.put(1, new AjouterPizzaOptionMenu(pizzaDao, scan));
		options.put(2, new ModifierPizzaOptionMenu(pizzaDao, scan));
		options.put(3, new SupprimerPizzaOptionMenu(pizzaDao, scan));
		options.put(99, new QuitterOptionMenu());

		Menu menu = new Menu(scan, options);
		menu.afficher();
	}
}
