package fr.pizzeria.ihm.menu.option;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import fr.pizzeria.console.ConsoleLogger;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Client;
import fr.pizzeria.model.Commande;
import fr.pizzeria.model.Pizza;

public class CommanderPizzaOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String COMMANDER_PIZZA_LIBELLE = "Commander une pizza";
	private Scanner scan;
	private Client client;

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO.
	 * @param scan Le scanner.
	 * @param client L'id du client connecté.
	 */
	public CommanderPizzaOptionMenu(IDaoFactory dao, Scanner scan, Client client) {
		super(COMMANDER_PIZZA_LIBELLE, dao);
		this.scan = scan;
		this.client = client;
	}

	@Override
	public boolean execute() throws DaoException {
		ConsoleLogger.out("Commande.");
		boolean stop = false;
		List<Pizza> pizzas = new ArrayList<>();
		while (!stop) {
			dao.getPizzaDao().findAllPizzas().stream().forEach(ConsoleLogger::out);
			ConsoleLogger.out("");
			ConsoleLogger.out("Veuillez saisir le code de la pizza.");
			ConsoleLogger.out("(99 pour valider la liste)");
			String code = scan.next();
			if ("99".equals(code)) {
				stop = true;
			} else {
				pizzas.add(dao.getPizzaDao().getPizza(code));
			}
		}
		dao.getCommandeDao().saveNewCommande(new Commande(Calendar.getInstance().getTime(), client, pizzas));
		ConsoleLogger.out("");
		return false;
	}
}
