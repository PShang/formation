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
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import fr.pizzeria.dao.GenericDaoFactory;
import fr.pizzeria.dao.IDaoFactory;
import fr.pizzeria.dao.client.IClientDao;
import fr.pizzeria.dao.commande.ICommandeDao;
import fr.pizzeria.dao.pizza.IPizzaDao;

@Configuration
@ComponentScan("fr.pizzeria")
@EnableTransactionManagement
@EnableJpaRepositories("fr.pizzeria.repos")
@EnableAspectJAutoProxy
public class PizzeriaAppSpringConfigJpa {

	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}

	@Bean
	public IDaoFactory daoFactory(@Qualifier("pizzaDaoJpaDataImpl") IPizzaDao pizzaDao, @Qualifier("clientDaoJpaImpl") IClientDao clientDao,
			@Qualifier("commandeDaoJpaImpl") ICommandeDao commandeDao) {
		return new GenericDaoFactory(pizzaDao, clientDao, commandeDao);
	}

	@Bean
	public PropertyPlaceholderConfigurer props() {
		PropertyPlaceholderConfigurer co = new PropertyPlaceholderConfigurer();
		co.setLocations(new ClassPathResource("jdbc.properties"));
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
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}
}