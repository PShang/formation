package fr.pizzeria.console;

import java.text.MessageFormat;
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
			ConsoleLogger.out("DAO : " + fileDaoImpl);
			try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(APPLICATION_CONFIG_XML, fileDaoImpl)) {
				Menu menu = context.getBean(Menu.class);
				menu.afficher();
			}
		} catch (MissingResourceException e) {
			ConsoleLogger.err(MessageFormat.format("Erreur: Le fichier {0}.properties doit contenir la propriété \"{1}\" désignant le fichier de configuration Spring pour la DAO.",
					FILE_APLLICATION_PROP, PROPERTY_DAO_IMPL), e);
		}
	}
}
