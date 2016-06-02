package fr.pizzeria.config;

import java.util.Scanner;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import fr.pizzeria.dao.GenericDaoFactory;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.dao.pizza.IPizzaDao;

@Configuration
@ComponentScan("fr.pizzeria")
@EnableTransactionManagement
public class PizzeriaAppSpringConfig {

	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}

	@Bean
	public IDaoFactory daoFactory(@Qualifier("pizzaDaoJdbcTemplateImpl") IPizzaDao pizzaDao) {
		return new GenericDaoFactory(pizzaDao, null, null);
	}

	@Bean
	public PropertyPlaceholderConfigurer props() {
		PropertyPlaceholderConfigurer co = new PropertyPlaceholderConfigurer();
		co.setLocations(new ClassPathResource("jdbc.properties"), new ClassPathResource("rest.properties"));
		return co;
	}

	@Bean
	public DataSource dataSource(@Value("${db.url}") String url, @Value("${db.user}") String user, @Value("${db.pass}") String pass) {
		return new DriverManagerDataSource(url, user, pass);
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		return Persistence.createEntityManagerFactory("pizzeria-console-objet-java8");
	}

	@Bean
	public PlatformTransactionManager transactionManager(@Value("${db.url}") String url, @Value("${db.user}") String user, @Value("${db.pass}") String pass) {
		return new DataSourceTransactionManager(dataSource(url, user, pass));
	}
}