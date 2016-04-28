package fr.pizzeria.exception;

public class SavePizzaException extends DaoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SavePizzaException() {
		super();
	}

	public SavePizzaException(String message, Throwable cause) {
		super(message, cause);
	}
}
