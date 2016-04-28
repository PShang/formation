package fr.pizzeria.ihm.menu.option;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.exception.DaoException;

public abstract class OptionMenu {

	private String libelle;
	protected IPizzaDao pizzaDao;

	public OptionMenu(String libelle) {
		this.libelle = libelle;
	}

	public OptionMenu(String libelle, IPizzaDao pizzaDao) {
		this(libelle);
		this.pizzaDao = pizzaDao;
	}

	public abstract boolean execute() throws DaoException;

	public String getLibelle() {
		return libelle;
	}
}
