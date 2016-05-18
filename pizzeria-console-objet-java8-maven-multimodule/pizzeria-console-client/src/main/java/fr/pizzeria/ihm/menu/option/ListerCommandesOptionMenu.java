package fr.pizzeria.ihm.menu.option;

import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Client;

public class ListerCommandesOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String LISTER_COMMANDES_LIBELLE = "Lister ses commandes";
	private Client client;

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO.
	 * @param scan Le scanner.
	 * @param clientId L'id du client connecté.
	 */
	public ListerCommandesOptionMenu(IDaoFactory dao, Client client) {
		super(LISTER_COMMANDES_LIBELLE, dao);
		this.client = client;
	}

	@Override
	public boolean execute() throws DaoException {
		client.getCommandes().forEach(System.out::println);
		return false;
	}
}
