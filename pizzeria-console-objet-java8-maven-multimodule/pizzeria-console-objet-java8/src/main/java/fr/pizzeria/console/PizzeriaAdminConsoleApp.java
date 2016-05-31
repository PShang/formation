package fr.pizzeria.console;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.pizzeria.dao.pizza.PizzaDaoImpl;
import fr.pizzeria.ihm.menu.Menu;

/**
 * Classe principale de l'application.
 */
public class PizzeriaAdminConsoleApp {
	private static final String APPLICATION_CONFIG_XML = "application-config.xml";
	private static final String FILE_APLLICATION_PROP = "application";
	private static final String PROPERTY_DAO_IMPL = "dao.impl";

	private PizzeriaAdminConsoleApp() {}

	/**
	 * Methode d'entrée. Initialise le {@link Menu}, le {@link Scanner} et la {@link PizzaDaoImpl DAO}.
	 * 
	 * @param args Les aguments du programme.
	 */
	public static void main(String[] args) {
		Logger.getLogger("org").setLevel(Level.SEVERE);
		Locale.setDefault(Locale.FRENCH);
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(FILE_APLLICATION_PROP);
			String fileDaoImpl = bundle.getString(PROPERTY_DAO_IMPL);
			System.out.println("DAO : " + fileDaoImpl);
			try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(APPLICATION_CONFIG_XML, fileDaoImpl)) {
				Menu menu = context.getBean(Menu.class);
				menu.afficher();
			}
		} catch (MissingResourceException e) {
			System.err.println(
					"Erreur: Le fichier " + FILE_APLLICATION_PROP + ".properties doit contenir la propriété \"" + PROPERTY_DAO_IMPL + "\" désignant le fichier de configuration Spring pour la DAO.");
		}
	}
}
