package fr.pizzeria.dao.performance;

import java.util.List;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Performance;

/**
 * Interface de DAO pour la gestion des {@link Performance}.
 */
public interface IPerformanceDao {

	/**
	 * Récupere la liste des performances.
	 * 
	 * @return Une {@link List}<{@link Performance}>.
	 */
	List<Performance> findAllPerformances();

	/**
	 * Récupere une Performance par son code.
	 * 
	 * @param id L'd de la performance à récupérer.
	 * @return Une {@link Performance}.
	 * @throws DaoException
	 */
	Performance getPerformance(Integer id) throws DaoException;

	/**
	 * Sauvegarde une nouvelle Performance.
	 * 
	 * @param performance La nouvelle Performance à sauvegarder.
	 * @throws DaoException
	 */
	void saveNewPerformance(Performance performance) throws DaoException;
}
