package fr.pizzeria.console;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.pizzeria.dao.DaoProducer;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.dao.pizza.PizzaDaoImpl;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.ihm.menu.Menu;
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
	private static final String BASEURL_REST = "http://localhost:8080/pizzeria-admin-app/api/rest";

	private PizzeriaAdminConsoleApp() {}

	/**
	 * Methode d'entrée. Initialise le {@link Menu}, le {@link Scanner} et la {@link PizzaDaoImpl DAO}.
	 * 
	 * @param args Les aguments du programme.
	 */
	public static void main(String[] args) {
		Locale.setDefault(Locale.FRENCH);
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(FILE_APLLICATION_PROP);
			String property = bundle.getString(PROPERTY_DAO_IMPL);
			int daoImpl = Integer.parseInt(property);
			IDaoFactory dao;
			EntityManagerFactory emf = null;
			switch (daoImpl) {
				case 0:
					System.out.println("DAO : Mémoire");
					dao = new DaoProducer().getDaoFactoryMemoire();
					break;
				case 1:
					System.out.println("DAO : FIchiers");
					dao = new DaoProducer().getDaoFactoryFichier();
					break;
				case 2:
					System.out.println("DAO : JDBC");
					bundle = ResourceBundle.getBundle(FILE_JDBC_PROP);
					String driverConnection = bundle.getString(PROPERTY_DRIVER);
					String urlConnection = bundle.getString(PROPERTY_URL);
					String userConnection = bundle.getString(PROPERTY_USER);
					String passConnection = bundle.getString(PROPERTY_PASS).isEmpty() ? null : bundle.getString(PROPERTY_PASS);
					try {
						Class.forName(driverConnection);
					} catch (ClassNotFoundException e) {
						System.err.println("Erreur : Le driver " + driverConnection + " est introuvable.");
					}
					try {
						dao = new DaoProducer().getDaoFactoryJdbc(urlConnection, userConnection, passConnection);
					} catch (SQLException e) {
						throw new DaoException("Erreur SQL : " + e.getMessage(), e);
					}
					break;
				case 3:
					System.out.println("DAO : JPA");
					Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
					emf = Persistence.createEntityManagerFactory("pizzeria-console-objet-java8");
					dao = new DaoProducer().getDaoFactoryJpa(emf);
					break;
				case 4:
					System.out.println("DAO : REST");
					dao = new DaoProducer().getDaoFactoryRest(BASEURL_REST);
					break;
				default:
					System.err.println("Erreur: Le fichier " + FILE_APLLICATION_PROP + ".properties doit contenir la propriété \"" + PROPERTY_DAO_IMPL + "\" avec la valeur 0, 1, 2, 3 ou 4.");
					return;
			}
			Scanner scan = new Scanner(System.in);
			Map<Integer, OptionMenu> options = new TreeMap<>();
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

			Menu menu = new Menu(scan, options);
			menu.afficher();
			scan.close();

			if (emf != null) {
				emf.close();
			}
		} catch (DaoException e) {
			System.err.println(e.getMessage());
		} catch (MissingResourceException e) {
			System.err.println("Erreur: Le fichier " + FILE_APLLICATION_PROP + ".properties doit contenir la propriété \"" + PROPERTY_DAO_IMPL + "\" avec la valeur 0, 1, 2, 3 ou 4.");
		}
	}
}
