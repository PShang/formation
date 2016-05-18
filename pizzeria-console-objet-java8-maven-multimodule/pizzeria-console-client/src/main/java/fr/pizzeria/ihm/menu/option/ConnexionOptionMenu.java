package fr.pizzeria.ihm.menu.option;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.SaveClientException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.ihm.menu.Menu;
import fr.pizzeria.model.Client;

public class ConnexionOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String CONNEXION_LIBELLE = "Se connecter";
	private Scanner scan;

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO.
	 */
	public ConnexionOptionMenu(IDaoFactory dao, Scanner scan) {
		super(CONNEXION_LIBELLE, dao);
		this.scan = scan;
	}

	@Override
	public boolean execute() throws DaoException {
		System.out.println("Connexion.");
		System.out.println("Veuillez saisir votre adresse email");
		String email = scan.next();
		System.out.println("Veuillez saisir votre mot de passe");
		String mdp = scan.next();
		try {
			Client client = dao.getClientDao().getClient(email, mdp);

			Map<Integer, OptionMenu> options = new TreeMap<>();
			options.put(1, new CommanderPizzaOptionMenu(dao, scan, client));
			options.put(2, new ListerCommandesOptionMenu(dao, client));
			options.put(99, new SortirOptionMenu());

			Menu menu = new Menu(scan, options);
			menu.afficher();

		} catch (SaveClientException e) {
			throw new SavePizzaException("Erreur : Le client avec l'adresse email " + email + " existe déjà.", e);
		}
		System.out.println();
		return false;
	}
}
