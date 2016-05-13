package fr.pizzeria.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	private JdbcHelper helper;

	private static final String TABLE_PIZZA = "pizza";
	private static final String COLUMN_CODE = "code";
	private static final String COLUMN_NOM = "nom";
	private static final String COLUMN_PRIX = "prix";
	private static final String COLUMN_CATEGORIE = "categorie";

	/**
	 * Constructeur. Initialise la {@link Map} de pizzas en lisant une base de
	 * données de pizzas.
	 * 
	 * @param jdbcHelper Le Helper pour la base de donnée
	 * 
	 * @throws DaoException
	 * @throws SQLException
	 */
	public PizzaDaoJdbcImpl(JdbcHelper jdbcHelper) throws DaoException, SQLException {
		helper = jdbcHelper;
		ResultSet results = helper.executeQuery("SELECT * FROM " + TABLE_PIZZA);
		while (results.next()) {
			String code = results.getString(COLUMN_CODE);
			String nom = results.getString(COLUMN_NOM);
			Double prix = results.getDouble(COLUMN_PRIX);
			String categorie = results.getString(COLUMN_CATEGORIE);
			Pizza p = new Pizza(code, nom, prix, CategoriePizza.valueOf(categorie));
			pizzas.put(p.getCode(), p);
		}
		results.close();
		helper.close();
	}

	@Override
	public List<Pizza> findAllPizzas() {
		return pizzas.values().stream().sorted(Comparator.comparing(Pizza::getNom)).collect(Collectors.toList());
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws DaoException {
		if (pizzas.containsKey(pizza.getCode())) {
			throw new SavePizzaException();
		}
		pizzas.put(pizza.getCode(), pizza);
		try {
			String request = String.format("INSERT INTO %s(%s, %s, %s, %s) VALUES(\"%s\", \"%s\", %s, \"%s\")",
					TABLE_PIZZA, COLUMN_CODE, COLUMN_NOM, COLUMN_PRIX, COLUMN_CATEGORIE, pizza.getCode(),
					pizza.getNom(), pizza.getPrix(), pizza.getCategorie().toString());
			helper.executeUpdate(request);
			helper.close();
		} catch (SQLException e) {
			throw new SavePizzaException("Erreur SQL lors de l'insertion des données.", e);
		}
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		if (!pizzas.containsKey(pizza.getCode())) {
			throw new UpdatePizzaException();
		}
		pizzas.put(codePizza, pizza);
		try {
			String request = String.format(
					"UPDATE %s SET %s = \"%s\", %s = \"%s\", %s = %s, %s = \"%s\" WHERE %s = \"%s\"", TABLE_PIZZA,
					COLUMN_CODE, pizza.getCode(), COLUMN_NOM, pizza.getNom(), COLUMN_PRIX, pizza.getPrix(),
					COLUMN_CATEGORIE, pizza.getCategorie().toString(), COLUMN_CODE, codePizza);
			helper.executeUpdate(request);
			helper.close();
		} catch (SQLException e) {
			throw new UpdatePizzaException("Erreur SQL lors de la mise à jour des données.", e);
		}
	}

	@Override
	public void deletePizza(String codePizza) throws DaoException {
		if (!pizzas.containsKey(codePizza)) {
			throw new DeletePizzaException();
		}
		pizzas.remove(codePizza);
		try {
			String request = String.format("DELETE FROM %s WHERE %s = \"%s\"", TABLE_PIZZA, COLUMN_CODE, codePizza);
			helper.executeUpdate(request);
			helper.close();
		} catch (SQLException e) {
			throw new DeletePizzaException("Erreur SQL lors de la suppression des données.", e);
		}
	}
}
