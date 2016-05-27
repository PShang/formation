package fr.pizzeria.ihm.menu.option.pizza;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.CategoriePizzaException;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class ModifierPizzaOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String MODIFIER_PIZZA_LIBELLE = "Modifier une pizza";
	private Scanner scan;

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO Factory.
	 * @param scan Le {@link Scanner} pour la saisie utilisateur.
	 */
	public ModifierPizzaOptionMenu(IDaoFactory dao, Scanner scan) {
		super(MODIFIER_PIZZA_LIBELLE, dao);
		this.scan = scan;
	}

	@Override
	public boolean execute() throws DaoException {
		System.out.println("Mise à jour d'une pizza");
		new ListerPizzaOptionMenu(dao).execute();
		System.out.println("Veuillez choisir le code la pizza à modifier.");
		System.out.println("(99 pour abandonner).");
		String code = scan.next();
		if ("99".equals(code)) {
			return false;
		}
		System.out.println("Veuillez saisir le nom (sans espace)");
		String name = scan.next();
		System.out.println("Veuillez saisir le prix");
		BigDecimal price = scan.nextBigDecimal();
		System.out.println("Veuillez saisir la catégorie : " + Arrays.toString(CategoriePizza.values()));
		String categorieString = scan.next();
		try {
			CategoriePizza categorie = CategoriePizza.valueOf(categorieString.toUpperCase());
			dao.getPizzaDao().updatePizza(code, new Pizza(code, name, price, categorie));
		} catch (UpdatePizzaException e) {
			throw new UpdatePizzaException("Erreur : La pizza avec le code " + code + " n'existe pas.", e);
		} catch (IllegalArgumentException e) {
			throw new CategoriePizzaException("Erreur de saisie : La catégorie \"" + categorieString + "\" n'existe pas.", e);
		}
		System.out.println();
		return false;
	}
}
