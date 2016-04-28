package fr.pizzeria.console;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaDaoImpl;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.ihm.menu.option.AjouterPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.ListerPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.ModifierPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.ihm.menu.option.QuitterOptionMenu;
import fr.pizzeria.ihm.menu.option.SupprimerPizzaOptionMenu;

public class PizzeriaAdminConsoleApp {

	/**
	 * Methode d'entrée. Crée le {@link Menu} et les {@link OptionMenu}
	 * 
	 * @param args Les aguments du programme.
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		IPizzaDao pizzaDao = new PizzaDaoImpl();
		Menu menu = new Menu(scan, new ListerPizzaOptionMenu(pizzaDao), new AjouterPizzaOptionMenu(pizzaDao, scan),
				new ModifierPizzaOptionMenu(pizzaDao, scan), new SupprimerPizzaOptionMenu(pizzaDao, scan),
				new QuitterOptionMenu());
		menu.afficher();
	}
}
