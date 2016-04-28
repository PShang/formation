package fr.pizzeria.exception;

public class DeletePizzaException extends DaoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeletePizzaException() {
		super();
	}

	public DeletePizzaException(String message, Throwable cause) {
		super(message, cause);
	}
}
