package fr.pizzeria.ihm.menu.option.client;

import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;

import fr.pizzeria.console.ConsoleLogger;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.model.Client;

/**
 * Option pour ajouter un nouveau client.
 */
public class AjouterClientOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String AJOUTER_CLIENT_LIBELLE = "Ajouter un client";
	private Scanner scan;

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO Factory.
	 * @param scan Le {@link Scanner} pour la saisie utilisateur.
	 */
	public AjouterClientOptionMenu(IDaoFactory dao, Scanner scan) {
		super(AJOUTER_CLIENT_LIBELLE, dao);
		this.scan = scan;
	}

	@Override
	public boolean execute() throws DaoException {
		ConsoleLogger.out("Ajout d'un nouveau client");
		ConsoleLogger.out("Veuillez saisir le nom");
		String nom = scan.next();
		ConsoleLogger.out("Veuillez saisir le prenom");
		String prenom = scan.next();
		ConsoleLogger.out("Veuillez saisir l'email");
		String email = scan.next();
		ConsoleLogger.out("Veuillez saisir le mdp");
		String mdp = scan.next();
		try {
			dao.getClientDao().saveNewClient(new Client(nom, prenom, email, DigestUtils.md5Hex(mdp).toString()));
		} catch (SavePizzaException e) {
			throw new SavePizzaException("Erreur : Le client avec l'adresse email " + email + " existe déjà.", e);
		}
		ConsoleLogger.out("");
		return false;
	}
}
