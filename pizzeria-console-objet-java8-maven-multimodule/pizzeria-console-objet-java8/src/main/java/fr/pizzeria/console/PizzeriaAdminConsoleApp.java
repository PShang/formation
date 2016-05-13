package fr.pizzeria.console;

import java.sql.SQLException;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.JdbcHelper;
import fr.pizzeria.dao.PizzaDaoFichierImpl;
import fr.pizzeria.dao.PizzaDaoImpl;
import fr.pizzeria.dao.PizzaDaoJdbcImpl;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.ihm.menu.option.AfficherPizzaPlusCherOptionMenu;
import fr.pizzeria.ihm.menu.option.AjouterPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.ListerPizzaCategorieOptionMenu;
import fr.pizzeria.ihm.menu.option.ListerPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.ModifierPizzaOptionMenu;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.ihm.menu.option.QuitterOptionMenu;
import fr.pizzeria.ihm.menu.option.SupprimerPizzaOptionMenu;

/**
 * Classe principale de l'application.
 */
public class PizzeriaAdminConsoleApp {
	private static final String FILE_APLLICATION_PROP = "application";
	private static final String FILE_JDBC_PROP = "jdbc";
	private static final String PROPERTY_DAO_IMPL = "dao.impl";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_USER = "user";
	private static final String PROPERTY_PASS = "pass";

	private PizzeriaAdminConsoleApp() {
	}

	/**
	 * Methode d'entrée. Initialise le {@link Menu}, le {@link Scanner} et la
	 * {@link PizzaDaoImpl DAO}.
	 * 
	 * @param args Les aguments du programme.
	 */
	public static void main(String[] args) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(FILE_APLLICATION_PROP);
			String property = bundle.getString(PROPERTY_DAO_IMPL);
			int daoImpl = Integer.parseInt(property);
			IPizzaDao pizzaDao;
			switch (daoImpl) {
				case 0:
					pizzaDao = new PizzaDaoImpl();
					break;
				case 1:
					pizzaDao = new PizzaDaoFichierImpl();
					break;
				case 2:
					bundle = ResourceBundle.getBundle(FILE_JDBC_PROP);
					String driverConnection = bundle.getString(PROPERTY_DRIVER);
					String urlConnection = bundle.getString(PROPERTY_URL);
					String userConnection = bundle.getString(PROPERTY_USER);
					String passConnection = bundle.getString(PROPERTY_PASS).isEmpty() ? null
							: bundle.getString(PROPERTY_PASS);
					try {
						pizzaDao = new PizzaDaoJdbcImpl(
								new JdbcHelper(driverConnection, urlConnection, userConnection, passConnection));
					} catch (SQLException e) {
						throw new DaoException("Erreur SQL : " + e.getMessage(), e);
					}
					break;
				default:
					System.err.println(
							"Erreur: Le fichier " + FILE_APLLICATION_PROP + ".properties doit contenir la propriété \""
									+ PROPERTY_DAO_IMPL + "\" avec la valeur 0, 1 ou 2.");
					return;
			}
			Scanner scan = new Scanner(System.in);
			Map<Integer, OptionMenu> options = new TreeMap<>();
			options.put(0, new ListerPizzaOptionMenu(pizzaDao));
			options.put(1, new AjouterPizzaOptionMenu(pizzaDao, scan));
			options.put(2, new ModifierPizzaOptionMenu(pizzaDao, scan));
			options.put(3, new SupprimerPizzaOptionMenu(pizzaDao, scan));
			options.put(4, new ListerPizzaCategorieOptionMenu(pizzaDao));
			options.put(5, new AfficherPizzaPlusCherOptionMenu(pizzaDao));
			options.put(99, new QuitterOptionMenu());

			Menu menu = new Menu(scan, options);
			menu.afficher();
		} catch (DaoException e) {
			System.err.println(e.getMessage());
		} catch (MissingResourceException e) {
			System.err.println("Erreur: Le fichier " + FILE_APLLICATION_PROP
					+ ".properties doit contenir la propriété \"" + PROPERTY_DAO_IMPL + "\" avec la valeur 0, 1 ou 2.");
		}
	}
}
