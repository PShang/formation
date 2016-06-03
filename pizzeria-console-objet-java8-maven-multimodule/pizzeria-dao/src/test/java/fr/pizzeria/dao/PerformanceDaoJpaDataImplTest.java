package fr.pizzeria.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import fr.pizzeria.config.PizzaDaoSpringConfigJpaTest;
import fr.pizzeria.dao.performance.IPerformanceDao;
import fr.pizzeria.dao.pizza.IPizzaDao;

@ContextConfiguration(classes = PizzaDaoSpringConfigJpaTest.class)
public class PerformanceDaoJpaDataImplTest extends PerformanceDaoTest {

	@Autowired
	public void setPerformanceDao(@Qualifier("performanceJpaDataImpl") IPerformanceDao performanceDao) {
		this.performanceDao = performanceDao;
	}
	
	@Autowired
	public void setPizzaDao(@Qualifier("pizzaDaoJpaDataImpl") IPizzaDao pizzaDao) {
		this.pizzaDao = pizzaDao;
	}

}
