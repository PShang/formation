package fr.pizzeria.ihm.menu.option.client;

import java.util.Scanner;

import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.ihm.menu.option.OptionMenu;

public class SupprimerClientOptionMenu extends OptionMenu {

	/**
	 * Constante pour le {@link OptionMenu.libelle libéllé} de l'{@link OptionMenu}.
	 */
	private static final String SUPPRIMER_CLIENT_LIBELLE = "Supprimer un client";
	private Scanner scan;

	/**
	 * Constructeur.
	 * 
	 * @param dao La DAO Factory.
	 * @param scan Le {@link Scanner} pour la saisie utilisateur.
	 */
	public SupprimerClientOptionMenu(IDaoFactory dao, Scanner scan) {
		super(SUPPRIMER_CLIENT_LIBELLE, dao);
		this.scan = scan;
	}

	@Override
	public boolean execute() throws DaoException {
		System.out.println("Suppression d'un client");
		new ListerClientOptionMenu(dao).execute();
		System.out.println("Veuillez choisir l'id du client à supprimer.");
		System.out.println("(99 pour abandonner).");
		Integer id = scan.nextInt();
		if (99 == id) {
			return false;
		}
		try {
			dao.getClientDao().deleteClient(id);
		} catch (DeletePizzaException e) {
			throw new DeletePizzaException("Erreur : Le client avec l'id " + id + " n'existe pas.", e);
		}
		System.out.println();
		return false;
	}
}
