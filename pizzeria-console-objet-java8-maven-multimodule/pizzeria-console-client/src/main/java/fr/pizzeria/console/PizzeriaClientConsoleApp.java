package fr.pizzeria.console;

import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.pizzeria.dao.GenericDaoFactory;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.dao.client.ClientDaoJpaImpl;
import fr.pizzeria.dao.commande.CommandeDaoJpaImpl;
import fr.pizzeria.dao.pizza.PizzaDaoJpaImpl;
import fr.pizzeria.dao.pizza.PizzaDaoMemoireImpl;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.ihm.menu.option.ConnexionOptionMenu;
import fr.pizzeria.ihm.menu.option.InscriptionOptionMenu;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.ihm.menu.option.QuitterOptionMenu;

/**
 * Classe principale de l'application.
 */
public class PizzeriaClientConsoleApp {

	private PizzeriaClientConsoleApp() {}

	/**
	 * Methode d'entrée. Initialise le {@link Menu}, le {@link Scanner} et la {@link PizzaDaoMemoireImpl DAO}.
	 * 
	 * @param args Les aguments du programme.
	 */
	public static void main(String[] args) {
		Locale.setDefault(Locale.FRENCH);
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pizzeria-console-client");
		IDaoFactory dao = new GenericDaoFactory(new PizzaDaoJpaImpl(emf), new ClientDaoJpaImpl(emf), new CommandeDaoJpaImpl(emf));
		Scanner scan = new Scanner(System.in);
		Map<Integer, OptionMenu> options = new TreeMap<>();
		options.put(1, new InscriptionOptionMenu(dao, scan));
		options.put(2, new ConnexionOptionMenu(dao, scan));
		options.put(99, new QuitterOptionMenu());

		Menu menu = new Menu(scan, options);
		menu.afficher();
		scan.close();

		emf.close();
	}
}
