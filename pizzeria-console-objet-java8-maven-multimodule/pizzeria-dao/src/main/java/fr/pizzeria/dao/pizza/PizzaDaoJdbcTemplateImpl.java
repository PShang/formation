package fr.pizzeria.dao.pizza;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections4.ListUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * Implémentation de la DAO JDBC pour les pizzas.
 */
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
	 * Constructeur. Initialise la {@link Map} de pizzas en lisant une base de données de pizzas.
	 * 
	 * @param dataSource La DataSource.
	 */
	public PizzaDaoJdbcTemplateImpl(DataSource dataSource, DataSourceTransactionManager txManager) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		transactionTemplate = new TransactionTemplate(txManager);
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
		return jdbcTemplate.queryForObject(MessageFormat.format("SELECT * FROM {0} WHERE {1} = ?", TABLE_PIZZA, COLUMN_CODE), Pizza.class, codePizza);
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws DaoException {
		jdbcTemplate.update(
				MessageFormat.format("INSERT INTO {0}({1}, {2}, {3}, {4}, {5}) VALUES(?, ?, ?, ?, ?)", TABLE_PIZZA, COLUMN_CODE, COLUMN_NOM, COLUMN_PRIX, COLUMN_CATEGORIE, COLUMN_URL_IMAGE),
				pizza.getCode(), pizza.getNom(), pizza.getPrix(), pizza.getCategorie().toString(), pizza.getUrlImage());
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		jdbcTemplate.update(MessageFormat.format("UPDATE {0} SET {1} = ?, {2} = ?, {3} = ?, {4} = ?, {5} = ? WHERE {6} = ?", TABLE_PIZZA, COLUMN_CODE, COLUMN_NOM, COLUMN_PRIX, COLUMN_CATEGORIE,
				COLUMN_URL_IMAGE, COLUMN_CODE), pizza.getCode(), pizza.getNom(), pizza.getPrix(), pizza.getCategorie().toString(), pizza.getUrlImage(), codePizza);
	}

	@Override
	public void deletePizza(String codePizza) throws DaoException {
		jdbcTemplate.update(MessageFormat.format("DELETE FROM {0} WHERE {1} = ?", TABLE_PIZZA, COLUMN_CODE), codePizza);
	}

	@Override
	public void importFromFiles(PizzaDaoFichierImpl pizzaDaoFichierImpl, int nb) throws DaoException {
		ListUtils.partition(pizzaDaoFichierImpl.findAllPizzas(), nb).forEach(list -> {
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
