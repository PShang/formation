package fr.pizzeria.ihm.menu.option.pizza;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

import fr.pizzeria.console.ConsoleLogger;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.CategoriePizzaException;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * Option pour ajouter une nouvelle pizza.
 */
public class AjouterPizzaOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String AJOUTER_PIZZA_LIBELLE = "Ajouter une pizza";
	private Scanner scan;

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO Factory.
	 * @param scan Le {@link Scanner} pour la saisie utilisateur.
	 */
	public AjouterPizzaOptionMenu(IDaoFactory dao, Scanner scan) {
		super(AJOUTER_PIZZA_LIBELLE, dao);
		this.scan = scan;
	}

	@Override
	public boolean execute() throws DaoException {
		ConsoleLogger.out("Ajout d'une nouvelle pizza");
		ConsoleLogger.out("Veuillez saisir le code");
		String code = scan.next();
		ConsoleLogger.out("Veuillez saisir le nom (sans espace)");
		String name = scan.next();
		ConsoleLogger.out("Veuillez saisir le prix");
		BigDecimal price = scan.nextBigDecimal();
		ConsoleLogger.out("Veuillez saisir la catégorie : " + Arrays.toString(CategoriePizza.values()));
		String categorieString = scan.next();
		try {
			CategoriePizza categorie = CategoriePizza.valueOf(categorieString.toUpperCase());
			dao.getPizzaDao().saveNewPizza(new Pizza(code, name, price, categorie));
		} catch (SavePizzaException e) {
			throw new SavePizzaException("Erreur : La pizza avec le code " + code + " existe déjà.", e);
		} catch (IllegalArgumentException e) {
			throw new CategoriePizzaException("Erreur de saisie : La catégorie \"" + categorieString + "\" n'existe pas.", e);
		}
		ConsoleLogger.out("");
		return false;
	}
}
