package fr.pizzeria.ihm.menu.option;

import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;

import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.SaveClientException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.model.Client;

public class InscriptionOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de
	 * l'{@link OptionMenu}.
	 */
	private static final String INSCRIPTION_LIBELLE = "S'inscrire";
	private Scanner scan;

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO.
	 */
	public InscriptionOptionMenu(IDaoFactory dao, Scanner scan) {
		super(INSCRIPTION_LIBELLE, dao);
		this.scan = scan;
	}

	@Override
	public boolean execute() throws DaoException {
		System.out.println("Inscription.");
		System.out.println("Veuillez saisir votre nom");
		String nom = scan.next();
		System.out.println("Veuillez saisir votre prénom");
		String prenom = scan.next();
		System.out.println("Veuillez saisir votre adresse email");
		String email = scan.next();
		System.out.println("Veuillez saisir votre mot de passe");
		String mdp = scan.next();
		try {
			dao.getClientDao().saveNewClient(new Client(nom, prenom, email, DigestUtils.md5Hex(mdp).toString()));
		} catch (SaveClientException e) {
			throw new SavePizzaException("Erreur : Le client avec l'adresse email " + email + " existe déjà.", e);
		}
		System.out.println();
		return false;
	}
}
