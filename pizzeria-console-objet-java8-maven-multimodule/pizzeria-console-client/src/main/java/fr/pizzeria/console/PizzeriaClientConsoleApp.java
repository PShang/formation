package fr.pizzeria.console;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Persistence;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaDaoImpl;
import fr.pizzeria.dao.PizzaDaoJpaImpl;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.ihm.menu.option.ConnexionOptionMenu;
import fr.pizzeria.ihm.menu.option.InscriptionOptionMenu;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.ihm.menu.option.QuitterOptionMenu;

/**
 * Classe principale de l'application.
 */
public class PizzeriaClientConsoleApp {
	private static final String FILE_APLLICATION_PROP = "application";
	private static final String PROPERTY_DAO_IMPL = "dao.impl";

	private PizzeriaClientConsoleApp() {
	}

	/**
	 * Methode d'entrée. Initialise le {@link Menu}, le {@link Scanner} et la
	 * {@link PizzaDaoImpl DAO}.
	 * 
	 * @param args Les aguments du programme.
	 */
	public static void main(String[] args) {
		Locale.setDefault(Locale.FRENCH);
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(FILE_APLLICATION_PROP);
			String property = bundle.getString(PROPERTY_DAO_IMPL);
			int daoImpl = Integer.parseInt(property);
			IPizzaDao pizzaDao = null;
			switch (daoImpl) {
				case 0:
				case 1:
				case 2:
					System.err.println("Erreur: Seule l'implémentation JPA est disponible avec la console client.");
					return;
				case 3:
					System.out.println("DAO : JPA");
					Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
					pizzaDao = new PizzaDaoJpaImpl(
							Persistence.createEntityManagerFactory("pizzeria-console-objet-java8"));
					break;
				default:
					System.err.println(
							"Erreur: Le fichier " + FILE_APLLICATION_PROP + ".properties doit contenir la propriété \""
									+ PROPERTY_DAO_IMPL + "\" avec la valeur 0, 1, 2 ou 3.");
					return;
			}
			Scanner scan = new Scanner(System.in);
			Map<Integer, OptionMenu> options = new TreeMap<>();
			options.put(1, new InscriptionOptionMenu(pizzaDao));
			options.put(2, new ConnexionOptionMenu(pizzaDao));
			options.put(99, new QuitterOptionMenu());

			Menu menu = new Menu(scan, options);
			menu.afficher();

			pizzaDao.close();
		} catch (MissingResourceException e) {
			System.err
					.println("Erreur: Le fichier " + FILE_APLLICATION_PROP + ".properties doit contenir la propriété \""
							+ PROPERTY_DAO_IMPL + "\" avec la valeur 0, 1, 2 ou 3.");
		}
	}
}
