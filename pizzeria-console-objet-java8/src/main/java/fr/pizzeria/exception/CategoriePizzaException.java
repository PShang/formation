package fr.pizzeria.exception;

/**
 * Exception pour les problèmes de sélection de catégorie des pizzas.
 */
public class CategoriePizzaException extends DaoException {

	private static final long serialVersionUID = 1L;

	public CategoriePizzaException() {
		super();
	}

	public CategoriePizzaException(String string, IllegalArgumentException e) {
		super(string, e);
	}
}
