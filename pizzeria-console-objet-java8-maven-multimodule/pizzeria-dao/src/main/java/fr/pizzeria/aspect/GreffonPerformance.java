package fr.pizzeria.aspect;

import java.util.Calendar;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.pizzeria.dao.performance.IPerformanceDao;
import fr.pizzeria.model.Performance;
import fr.pizzeria.model.Pizza;

@Aspect
@Component
public class GreffonPerformance {

	@Autowired IPerformanceDao performanceDao;

	@Around("execution(* fr.pizzeria.dao.pizza.IPizzaDao.*(..)) || execution(* fr.pizzeria.dao.client.IClientDao.*(..)) || execution(* fr.pizzeria.dao.commande.ICommandeDao.*(..))")
	public Object calcExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
		long before = Calendar.getInstance().getTimeInMillis();
		Object obj = pjp.proceed();
		long after = Calendar.getInstance().getTimeInMillis();
		long tempsExecution = after - before;
		Performance p = new Performance(pjp.getSignature().toShortString(), Calendar.getInstance().getTime(), tempsExecution);
		performanceDao.saveNewPerformance(p);
		return obj;
	}

	@Before("execution(* fr.pizzeria.dao.pizza.IPizzaDao.saveNewPizza(..)) && args(pizza)")
	public void generateCodePizza(Pizza pizza) {
		if (pizza.getCode() == null || pizza.getCode().isEmpty()) {
			pizza.setCode(pizza.getNom().substring(0, 3).toUpperCase());
		}
	}
}
