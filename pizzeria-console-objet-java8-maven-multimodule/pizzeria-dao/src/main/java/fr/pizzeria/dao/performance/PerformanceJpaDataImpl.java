package fr.pizzeria.dao.performance;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Performance;
import fr.pizzeria.repos.IPerformanceRepository;

/**
 * Implémentation de la DAO JPA Data pour les performances.
 */
@Repository
@Lazy
public class PerformanceJpaDataImpl implements IPerformanceDao {

	@Autowired private IPerformanceRepository performanceRepository;

	public PerformanceJpaDataImpl() {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Création du bean " + this.getClass().getName());
	}

	@Override
	public List<Performance> findAllPerformances() {
		return performanceRepository.findAll();
	}

	@Override
	public Performance getPerformance(Integer id) throws DaoException {
		return performanceRepository.findOne(id);
	}

	@Override
	@Transactional
	public void saveNewPerformance(Performance performance) throws DaoException {
		performanceRepository.save(performance);
	}
}
