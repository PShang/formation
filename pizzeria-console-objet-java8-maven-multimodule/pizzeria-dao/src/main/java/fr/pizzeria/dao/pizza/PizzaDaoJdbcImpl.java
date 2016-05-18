package fr.pizzeria.dao.pizza;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * Implémentation de la DAO utilisant une {@link Map} pour les pizzas.
 */
public class PizzaDaoJdbcImpl implements IPizzaDao {

	/**
	 * La {@link Map} pour les pizzas.
	 */
	private Map<String, Pizza> pizzas = new HashMap<>();

	private String urlConnection;
	private String userConnection;
	private String passConnection;

	private static final String TABLE_PIZZA = "pizza";
	private static final String COLUMN_CODE = "code";
	private static final String COLUMN_NOM = "nom";
	private static final String COLUMN_PRIX = "prix";
	private static final String COLUMN_CATEGORIE = "categorie";

	/**
	 * Constructeur. Initialise la {@link Map} de pizzas en lisant une base de données de pizzas.
	 * 
	 * @param jdbcHelper Le Helper pour la base de donnée
	 * 
	 * @throws DaoException
	 * @throws SQLException
	 */
	public PizzaDaoJdbcImpl(String urlConnection, String userConnection, String passConnection)
			throws DaoException, SQLException {
		this.urlConnection = urlConnection;
		this.userConnection = userConnection;
		this.passConnection = passConnection;
		try (Connection connection = DriverManager.getConnection(urlConnection, userConnection, passConnection);
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + TABLE_PIZZA);
				ResultSet results = statement.executeQuery();) {
			while (results.next()) {
				String code = results.getString(COLUMN_CODE);
				String nom = results.getString(COLUMN_NOM);
				BigDecimal prix = results.getBigDecimal(COLUMN_PRIX);
				String categorie = results.getString(COLUMN_CATEGORIE);
				Pizza p = new Pizza(code, nom, prix, CategoriePizza.valueOf(categorie));
				pizzas.put(p.getCode(), p);
			}
		}
	}

	@Override
	public List<Pizza> findAllPizzas() {
		return pizzas.values().stream().sorted(Comparator.comparing(Pizza::getNom)).collect(Collectors.toList());
	}

	@Override
	public Pizza getPizza(String code) {
		return pizzas.get(code);
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws DaoException {
		if (pizzas.containsKey(pizza.getCode())) {
			throw new SavePizzaException();
		}
		try (Connection connection = DriverManager.getConnection(urlConnection, userConnection, passConnection);
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO " + TABLE_PIZZA + "(" + COLUMN_CODE + ", " + COLUMN_NOM + ", "
								+ COLUMN_PRIX + ", " + COLUMN_CATEGORIE + ") VALUES(?, ?, ?, ?)");) {
			statement.setString(1, pizza.getCode());
			statement.setString(2, pizza.getNom());
			statement.setBigDecimal(3, pizza.getPrix());
			statement.setString(4, pizza.getCategorie().toString());
			statement.executeUpdate();
			pizzas.put(pizza.getCode(), pizza);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new SavePizzaException("Erreur SQL lors de l'insertion des données.", e);
		}
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		if (!pizzas.containsKey(pizza.getCode())) {
			throw new UpdatePizzaException();
		}
		try (Connection connection = DriverManager.getConnection(urlConnection, userConnection, passConnection);
				PreparedStatement statement = connection.prepareStatement(
						"UPDATE " + TABLE_PIZZA + " SET " + COLUMN_CODE + " = ?, " + COLUMN_NOM + " = ?, " + COLUMN_PRIX
								+ " = ?, " + COLUMN_CATEGORIE + " = ? WHERE " + COLUMN_CODE + " = ?");) {
			statement.setString(1, pizza.getCode());
			statement.setString(2, pizza.getNom());
			statement.setBigDecimal(3, pizza.getPrix());
			statement.setString(4, pizza.getCategorie().toString());
			statement.setString(5, codePizza);
			if (statement.executeUpdate() == 1) {
				pizzas.put(codePizza, pizza);
			}
		} catch (SQLException e) {
			throw new UpdatePizzaException("Erreur SQL lors de la mise à jour des données.", e);
		}
	}

	@Override
	public void deletePizza(String codePizza) throws DaoException {
		if (!pizzas.containsKey(codePizza)) {
			throw new DeletePizzaException();
		}
		try (Connection connection = DriverManager.getConnection(urlConnection, userConnection, passConnection);
				PreparedStatement statement = connection
						.prepareStatement("DELETE FROM " + TABLE_PIZZA + " WHERE " + COLUMN_CODE + " = ?");) {
			statement.setString(1, codePizza);
			if (statement.executeUpdate() == 1) {
				pizzas.remove(codePizza);
			}
		} catch (SQLException e) {
			throw new DeletePizzaException("Erreur SQL lors de la suppression des données.", e);
		}
	}

	@Override
	public void importFromFiles(PizzaDaoFichierImpl pizzaDaoFichierImpl, int nb) throws DaoException {
		for (List<Pizza> list : ListUtils.partition(pizzaDaoFichierImpl.findAllPizzas(), nb)) {
			try (Connection connection = DriverManager.getConnection(urlConnection, userConnection, passConnection);) {
				connection.setAutoCommit(false);
				try (PreparedStatement statement = connection
						.prepareStatement("INSERT INTO " + TABLE_PIZZA + "(" + COLUMN_CODE + ", " + COLUMN_NOM + ", "
								+ COLUMN_PRIX + ", " + COLUMN_CATEGORIE + ") VALUES(?, ?, ?, ?)");) {
					try {
						for (Pizza pizza : list) {
							if (pizzas.containsKey(pizza.getCode())) {
								throw new SavePizzaException(
										"Erreur : La pizza avec le code " + pizza.getCode() + " existe déjà.", null);
							}
							statement.setString(1, pizza.getCode());
							statement.setString(2, pizza.getNom());
							statement.setBigDecimal(3, pizza.getPrix());
							statement.setString(4, pizza.getCategorie().toString());
							statement.executeUpdate();
							pizzas.put(pizza.getCode(), pizza);
						}
						connection.commit();
					} catch (SQLException e) {
						connection.rollback();
						throw new SQLException(e);
					}
				}
			} catch (SQLException e) {
				throw new SavePizzaException("Erreur SQL lors de l'insertion des données.", e);
			}
		}
	}
}
