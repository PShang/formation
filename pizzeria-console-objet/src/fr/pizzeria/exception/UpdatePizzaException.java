package fr.pizzeria.exception;

public class UpdatePizzaException extends DaoException {

	private static final long serialVersionUID = 1L;

	public UpdatePizzaException() {
		super();
	}

	public UpdatePizzaException(String string, DaoException e) {
		super(string, e);
	}
}
