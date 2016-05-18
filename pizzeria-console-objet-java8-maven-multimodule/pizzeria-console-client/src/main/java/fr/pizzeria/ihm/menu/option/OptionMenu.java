package fr.pizzeria.ihm.menu.option;

import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.ihm.menu.Menu;

/**
 * Classe abstraite pour les Options du {@link Menu}.
 */
public abstract class OptionMenu {

	/**
	 * Le libéllé de l'{@link OptionMenu}.
	 */
	private String libelle;
	protected IDaoFactory dao;

	/**
	 * Constructeur.
	 * 
	 * @param libelle Le libéllé de l'{@link OptionMenu}.
	 */
	public OptionMenu(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Constructeur.
	 * 
	 * @param libelle Le libéllé de l'{@link OptionMenu}.
	 * @param dao La DAO.
	 */
	public OptionMenu(String libelle, IDaoFactory dao) {
		this(libelle);
		this.dao = dao;
	}

	/**
	 * Execute l'action.
	 * 
	 * @return {@code true} si l'action doit arrêter l'execution de l'application après son execution.
	 * @throws DaoException
	 */
	public abstract boolean execute() throws DaoException;

	/**
	 * Récupère le libéllé.
	 * 
	 * @return Le libéllé de l'{@link OptionMenu}.
	 */
	public String getLibelle() {
		return libelle;
	}
}
