package fr.pizzeria.ihm.menu.option.pizza;

import java.util.Scanner;

import fr.pizzeria.console.ConsoleLogger;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.ihm.menu.option.OptionMenu;

public class SupprimerPizzaOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String SUPPRIMER_PIZZA_LIBELLE = "Supprimer une pizza";
	private Scanner scan;

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO Factory.
	 * @param scan Le {@link Scanner} pour la saisie utilisateur.
	 */
	public SupprimerPizzaOptionMenu(IDaoFactory dao, Scanner scan) {
		super(SUPPRIMER_PIZZA_LIBELLE, dao);
		this.scan = scan;
	}

	@Override
	public boolean execute() throws DaoException {
		ConsoleLogger.out("Suppression d'une pizza");
		new ListerPizzaOptionMenu(dao).execute();
		ConsoleLogger.out("Veuillez choisir le code la pizza à supprimer.");
		ConsoleLogger.out("(99 pour abandonner).");
		String oldCode = scan.next();
		if ("99".equals(oldCode)) {
			return false;
		}
		try {
			dao.getPizzaDao().deletePizza(oldCode);
		} catch (DeletePizzaException e) {
			throw new DeletePizzaException("Erreur : La pizza avec le code " + oldCode + " n'existe pas.", e);
		}
		ConsoleLogger.out("");
		return false;
	}
}
