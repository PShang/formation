package fr.pizzeria.ihm.menu.option.client;

import fr.pizzeria.console.ConsoleLogger;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.ihm.menu.option.OptionMenu;

public class ListerClientOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String LISTER_CLIENTS_LIBELLE = "Lister les clients";

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO Factory.
	 */
	public ListerClientOptionMenu(IDaoFactory dao) {
		super(LISTER_CLIENTS_LIBELLE, dao);
	}

	@Override
	public boolean execute() {
		dao.getClientDao().findAllClients().stream().forEach(ConsoleLogger::out);
		ConsoleLogger.out("");
		return false;
	}
}
