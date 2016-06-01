package fr.pizzeria.ihm.menu.option.client;

import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;

import fr.pizzeria.console.ConsoleLogger;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.ihm.menu.option.OptionMenu;
import fr.pizzeria.model.Client;

public class ModifierClientOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String MODIFIER_CLIENT_LIBELLE = "Modifier un client";
	private Scanner scan;

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO Factory.
	 * @param scan Le {@link Scanner} pour la saisie utilisateur.
	 */
	public ModifierClientOptionMenu(IDaoFactory dao, Scanner scan) {
		super(MODIFIER_CLIENT_LIBELLE, dao);
		this.scan = scan;
	}

	@Override
	public boolean execute() throws DaoException {
		ConsoleLogger.out("Mise à jour d'un client");
		new ListerClientOptionMenu(dao).execute();
		ConsoleLogger.out("Veuillez choisir l'id du client à modifier.");
		ConsoleLogger.out("(99 pour abandonner).");
		Integer id = scan.nextInt();
		if (99 == id) {
			return false;
		}
		ConsoleLogger.out("Veuillez saisir le nom");
		String nom = scan.next();
		ConsoleLogger.out("Veuillez saisir le prenom");
		String prenom = scan.next();
		ConsoleLogger.out("Veuillez saisir l'email");
		String email = scan.next();
		ConsoleLogger.out("Veuillez saisir le mdp");
		String mdp = scan.next();
		try {
			dao.getClientDao().updateClient(id, new Client(nom, prenom, email, DigestUtils.md5Hex(mdp).toString()));
		} catch (UpdatePizzaException e) {
			throw new UpdatePizzaException("Erreur : Le client avec l'id " + id + " n'existe pas.", e);
		}
		ConsoleLogger.out("");
		return false;
	}
}
