package fr.pizzeria.dao.pizza;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * Implémentation de la DAO JDBC Template pour les pizzas.
 */
@Repository
@Lazy
public class PizzaDaoJdbcTemplateImpl implements IPizzaDao {
	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;

	private static final String TABLE_PIZZA = "pizza";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_CODE = "code";
	private static final String COLUMN_NOM = "nom";
	private static final String COLUMN_PRIX = "prix";
	private static final String COLUMN_CATEGORIE = "categorie";
	private static final String COLUMN_URL_IMAGE = "url_image";

	/**
	 * Constructeur.
	 * 
	 * @param dataSource La {@link DataSource}.
	 * @param transactionManager Le {@link TransactionManager}
	 */
	@Autowired
	public PizzaDaoJdbcTemplateImpl(DataSource dataSource, PlatformTransactionManager transactionManager) {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "===== Création du bean " + this.getClass().getName() + " =====");
		jdbcTemplate = new JdbcTemplate(dataSource);
		transactionTemplate = new TransactionTemplate(transactionManager);
	}

	@Override
	public List<Pizza> findAllPizzas() {
		return jdbcTemplate.query(MessageFormat.format("SELECT * FROM {0} ORDER BY {1}", TABLE_PIZZA, COLUMN_NOM), (rs, rowNum) -> {
			Pizza p = new Pizza();
			p.setId(rs.getInt(COLUMN_ID));
			p.setCode(rs.getString(COLUMN_CODE));
			p.setNom(rs.getString(COLUMN_NOM));
			p.setPrix(rs.getBigDecimal(COLUMN_PRIX));
			p.setCategorie(CategoriePizza.valueOf(rs.getString(COLUMN_CATEGORIE)));
			p.setUrlImage(rs.getString(COLUMN_URL_IMAGE));
			return p;
		});
	}

	@Override
	public Pizza getPizza(String codePizza) {
		Pizza pizza;
		try {
			pizza = jdbcTemplate.queryForObject(MessageFormat.format("SELECT * FROM {0} WHERE {1} = ?", TABLE_PIZZA, COLUMN_CODE), (rs, rowNum) -> {
				Pizza p = new Pizza();
				p.setId(rs.getInt(COLUMN_ID));
				p.setCode(rs.getString(COLUMN_CODE));
				p.setNom(rs.getString(COLUMN_NOM));
				p.setPrix(rs.getBigDecimal(COLUMN_PRIX));
				p.setCategorie(CategoriePizza.valueOf(rs.getString(COLUMN_CATEGORIE)));
				p.setUrlImage(rs.getString(COLUMN_URL_IMAGE));
				return p;
			}, codePizza);
		} catch (EmptyResultDataAccessException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "La pizza avec le code " + codePizza + " n'existe pas.", e);
			pizza = null;
		}
		return pizza;

	}

	@Override
	public void saveNewPizza(Pizza pizza) throws DaoException {
		try {
			jdbcTemplate.update(
					MessageFormat.format("INSERT INTO {0}({1}, {2}, {3}, {4}, {5}) VALUES(?, ?, ?, ?, ?)", TABLE_PIZZA, COLUMN_CODE, COLUMN_NOM, COLUMN_PRIX, COLUMN_CATEGORIE, COLUMN_URL_IMAGE),
					pizza.getCode(), pizza.getNom(), pizza.getPrix(), pizza.getCategorie().toString(), pizza.getUrlImage());
		} catch (DuplicateKeyException e) {
			throw new SavePizzaException("La pizza avec le code " + pizza.getCode() + " existe déjà.", e);
		}
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		if (0 == jdbcTemplate.update(MessageFormat.format("UPDATE {0} SET {1} = ?, {2} = ?, {3} = ?, {4} = ?, {5} = ? WHERE {6} = ?", TABLE_PIZZA, COLUMN_CODE, COLUMN_NOM, COLUMN_PRIX,
				COLUMN_CATEGORIE, COLUMN_URL_IMAGE, COLUMN_CODE), pizza.getCode(), pizza.getNom(), pizza.getPrix(), pizza.getCategorie().toString(), pizza.getUrlImage(), codePizza))
			throw new UpdatePizzaException();
	}

	@Override
	public void deletePizza(String codePizza) throws DaoException {
		if (0 == jdbcTemplate.update(MessageFormat.format("DELETE FROM {0} WHERE {1} = ?", TABLE_PIZZA, COLUMN_CODE), codePizza))
			throw new DeletePizzaException();
	}

	@Override
	public void saveAllPizzas(List<Pizza> pizzas, int nb) throws DaoException {
		ListUtils.partition(pizzas, nb).forEach(list -> {
			transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			transactionTemplate.execute((TransactionStatus status) -> {
				list.forEach(pizza -> jdbcTemplate.update(
						MessageFormat.format("INSERT INTO {0}({1}, {2}, {3}, {4}, {5}) VALUES(?, ?, ?, ?, ?)", TABLE_PIZZA, COLUMN_CODE, COLUMN_NOM, COLUMN_PRIX, COLUMN_CATEGORIE, COLUMN_URL_IMAGE),
						pizza.getCode(), pizza.getNom(), pizza.getPrix(), pizza.getCategorie().toString(), pizza.getUrlImage()));
				return null;
			});
		});
	}
}
