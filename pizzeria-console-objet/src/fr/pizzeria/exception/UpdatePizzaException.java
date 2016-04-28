package fr.pizzeria.exception;

public class UpdatePizzaException extends DaoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UpdatePizzaException() {
		super();
	}

	public UpdatePizzaException(String message, Throwable cause) {
		super(message, cause);
	}
}
