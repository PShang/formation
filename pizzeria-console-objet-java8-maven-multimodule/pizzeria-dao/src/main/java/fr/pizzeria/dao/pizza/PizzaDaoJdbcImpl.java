package fr.pizzeria.dao.pizza;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.collections4.ListUtils;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * Implémentation de la DAO JDBC pour les pizzas.
 */
public class PizzaDaoJdbcImpl implements IPizzaDao {

	private String urlConnection;
	private String userConnection;
	private String passConnection;

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
	 * @param jdbcHelper Le Helper pour la base de donnée
	 * 
	 * @throws DaoException
	 * @throws SQLException
	 */
	public PizzaDaoJdbcImpl(String driverConnection, String urlConnection, String userConnection, String passConnection) throws DaoException, SQLException {
		try {
			Class.forName(driverConnection);
		} catch (ClassNotFoundException e) {
			throw new DaoException("Erreur : Le driver " + driverConnection + " est introuvable.", e);
		}
		this.urlConnection = urlConnection;
		this.userConnection = userConnection;
		this.passConnection = passConnection;
	}

	@Override
	public List<Pizza> findAllPizzas() {
		List<Pizza> pizzas = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(urlConnection, userConnection, passConnection);
				PreparedStatement statement = connection.prepareStatement(MessageFormat.format("SELECT * FROM {0} ORDER BY {1}", TABLE_PIZZA, COLUMN_NOM));
				ResultSet results = statement.executeQuery();) {
			while (results.next()) {
				Integer id = results.getInt(COLUMN_ID);
				String code = results.getString(COLUMN_CODE);
				String nom = results.getString(COLUMN_NOM);
				BigDecimal prix = results.getBigDecimal(COLUMN_PRIX);
				String categorie = results.getString(COLUMN_CATEGORIE);
				String urlImage = results.getString(COLUMN_URL_IMAGE);
				Pizza p = new Pizza(id, code, nom, prix, CategoriePizza.valueOf(categorie), urlImage);
				pizzas.add(p);
			}
		} catch (SQLException e) {
			Logger.getGlobal().log(Level.SEVERE, "Erreur SQL : " + e.getMessage(), e);
		}
		return pizzas;
	}

	@Override
	public Pizza getPizza(String codePizza) {
		Pizza p = null;
		try (Connection connection = DriverManager.getConnection(urlConnection, userConnection, passConnection);
				PreparedStatement statement = connection.prepareStatement(MessageFormat.format("SELECT * FROM {0} WHERE {1} = ?", TABLE_PIZZA, COLUMN_CODE));) {
			statement.setString(1, codePizza);
			ResultSet results = statement.executeQuery();
			results.first();
			Integer id = results.getInt(COLUMN_ID);
			String code = results.getString(COLUMN_CODE);
			String nom = results.getString(COLUMN_NOM);
			BigDecimal prix = results.getBigDecimal(COLUMN_PRIX);
			String categorie = results.getString(COLUMN_CATEGORIE);
			String urlImage = results.getString(COLUMN_URL_IMAGE);
			p = new Pizza(id, code, nom, prix, CategoriePizza.valueOf(categorie), urlImage);
		} catch (SQLException e) {
			Logger.getGlobal().log(Level.SEVERE, "Erreur SQL : " + e.getMessage(), e);
		}
		return p;
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws DaoException {
		try (Connection connection = DriverManager.getConnection(urlConnection, userConnection, passConnection);
				PreparedStatement statement = connection.prepareStatement(MessageFormat.format("INSERT INTO {0}({1}, {2}, {3}, {4}, {5}) VALUES(?, ?, ?, ?, ?)", TABLE_PIZZA, COLUMN_CODE, COLUMN_NOM,
						COLUMN_PRIX, COLUMN_CATEGORIE, COLUMN_URL_IMAGE));) {
			statement.setString(1, pizza.getCode());
			statement.setString(2, pizza.getNom());
			statement.setBigDecimal(3, pizza.getPrix());
			statement.setString(4, pizza.getCategorie().toString());
			statement.setString(5, pizza.getUrlImage());
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new SavePizzaException("Erreur SQL lors de l'insertion des données.", e);
		}
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		try (Connection connection = DriverManager.getConnection(urlConnection, userConnection, passConnection);
				PreparedStatement statement = connection.prepareStatement(MessageFormat.format("UPDATE {0} SET {1} = ?, {2} = ?, {3} = ?, {4} = ?, {5} = ? WHERE {6} = ?", TABLE_PIZZA, COLUMN_CODE,
						COLUMN_NOM, COLUMN_PRIX, COLUMN_CATEGORIE, COLUMN_URL_IMAGE, COLUMN_CODE));) {
			statement.setString(1, pizza.getCode());
			statement.setString(2, pizza.getNom());
			statement.setBigDecimal(3, pizza.getPrix());
			statement.setString(4, pizza.getCategorie().toString());
			statement.setString(5, pizza.getUrlImage());
			statement.setString(6, codePizza);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new UpdatePizzaException("Erreur SQL lors de la mise à jour des données.", e);
		}
	}

	@Override
	public void deletePizza(String codePizza) throws DaoException {
		try (Connection connection = DriverManager.getConnection(urlConnection, userConnection, passConnection);
				PreparedStatement statement = connection.prepareStatement(MessageFormat.format("DELETE FROM {0} WHERE {1} = ?", TABLE_PIZZA, COLUMN_CODE));) {
			statement.setString(1, codePizza);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DeletePizzaException("Erreur SQL lors de la suppression des données.", e);
		}
	}

	@Override
	public void importFromFiles(PizzaDaoFichierImpl pizzaDaoFichierImpl, int nb) throws DaoException {
		for (List<Pizza> list : ListUtils.partition(pizzaDaoFichierImpl.findAllPizzas(), nb)) {
			try (Connection connection = DriverManager.getConnection(urlConnection, userConnection, passConnection);) {
				connection.setAutoCommit(false);
				importFromList(list, connection);
			} catch (SQLException e) {
				throw new SavePizzaException("Erreur SQL lors de l'insertion des données.", e);
			}
		}
	}

	private void importFromList(List<Pizza> list, Connection connection) throws SQLException {
		try (PreparedStatement statement = connection
				.prepareStatement(MessageFormat.format("INSERT INTO {0}({1}, {2}, {3}, {4}) VALUES(?, ?, ?, ?)", TABLE_PIZZA, COLUMN_CODE, COLUMN_NOM, COLUMN_PRIX, COLUMN_CATEGORIE));) {
			try {
				for (Pizza pizza : list) {
					statement.setString(1, pizza.getCode());
					statement.setString(2, pizza.getNom());
					statement.setBigDecimal(3, pizza.getPrix());
					statement.setString(4, pizza.getCategorie().toString());
					statement.executeUpdate();
				}
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				throw new SQLException(e);
			}
		}
	}
}
