package fr.pizzeria.spring.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import fr.pizzeria.dao.performance.IPerformanceDao;
import fr.pizzeria.dao.performance.PerformanceJpaDataImpl;
import fr.pizzeria.dao.pizza.BatchPizzaDaoJpaData;
import fr.pizzeria.dao.pizza.IPizzaDao;
import fr.pizzeria.dao.pizza.PizzaDaoJpaDataImpl;

@Configuration
@EnableWebMvc
@ComponentScan({ "fr.pizzeria.spring.mvc.controller", "fr.pizzeria.aspect" })
@EnableTransactionManagement
@EnableJpaRepositories("fr.pizzeria.repos")
@EnableAspectJAutoProxy
public class PizzeriaSpringConfig {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setPrefix("/WEB-INF/views/");
		vr.setSuffix(".jsp");
		return vr;
	}

	@Bean
	public IPizzaDao getPizzaDao() {
		return new PizzaDaoJpaDataImpl();
	}

	@Bean
	public IPerformanceDao getPerformanceDao() {
		return new PerformanceJpaDataImpl();
	}

	@Bean
	public LocalEntityManagerFactoryBean entityManagerFactory() {
		LocalEntityManagerFactoryBean lemfb = new LocalEntityManagerFactoryBean();
		lemfb.setPersistenceUnitName("pizzeria-spring-web");
		return lemfb;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

	@Bean
	public BatchPizzaDaoJpaData batchJpa() {
		return new BatchPizzaDaoJpaData();
	}
}
