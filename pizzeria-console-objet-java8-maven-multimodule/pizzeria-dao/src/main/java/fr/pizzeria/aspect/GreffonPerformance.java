package fr.pizzeria.aspect;

import java.util.Calendar;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.pizzeria.dao.performance.IPerformanceDao;
import fr.pizzeria.model.Performance;

@Aspect
@Component
public class GreffonPerformance {

	@Autowired IPerformanceDao performanceDao;

	@Around("execution(* fr.pizzeria.dao.pizza.*.*(..))")
	public Object calcExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
		long before = Calendar.getInstance().getTimeInMillis();
		Object obj = pjp.proceed();
		long after = Calendar.getInstance().getTimeInMillis();
		long tempsExecution = after - before;
		Performance p = new Performance(pjp.getSignature().toShortString(), Calendar.getInstance().getTime(), tempsExecution);
		performanceDao.saveNewPerformance(p);
		return obj;
	}
}
