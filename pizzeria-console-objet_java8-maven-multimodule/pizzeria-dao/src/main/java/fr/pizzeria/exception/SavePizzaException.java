package fr.pizzeria.exception;

/**
 * Exception pour les problèmes d'ajouts des pizzas.
 */
public class SavePizzaException extends DaoException {

	private static final long serialVersionUID = 1L;

	public SavePizzaException() {
		super();
	}

	public SavePizzaException(String string, DaoException e) {
		super(string, e);
	}
}
