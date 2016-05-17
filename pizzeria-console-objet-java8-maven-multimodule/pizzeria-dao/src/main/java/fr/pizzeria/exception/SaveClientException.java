package fr.pizzeria.exception;

/**
 * Exception pour les problèmes d'ajouts des clients.
 */
public class SaveClientException extends DaoException {

	private static final long serialVersionUID = 1L;

	public SaveClientException() {
		super();
	}

	public SaveClientException(String string, Throwable e) {
		super(string, e);
	}
}
