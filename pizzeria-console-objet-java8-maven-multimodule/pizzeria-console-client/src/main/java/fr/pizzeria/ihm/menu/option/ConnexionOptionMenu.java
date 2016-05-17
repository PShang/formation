package fr.pizzeria.ihm.menu.option;

import java.util.Scanner;

import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.SaveClientException;
import fr.pizzeria.exception.SavePizzaException;

public class ConnexionOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de
	 * l'{@link OptionMenu}.
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
			Integer clientId = dao.getClientDao().getClient(email, mdp);
			// TODO faire le sous menu
		} catch (SaveClientException e) {
			throw new SavePizzaException("Erreur : Le client avec l'adresse email " + email + " existe déjà.", e);
		}
		System.out.println();
		return false;
	}
}
