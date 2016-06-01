package fr.pizzeria.dao.pizza;

import java.math.BigDecimal;
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
public class PizzaDaoImpl implements IPizzaDao {

	/**
	 * La {@link Map} pour les pizzas.
	 */
	private Map<String, Pizza> pizzas;

	/**
	 * Constructeur. Initialise la {@link Map} de pizzas.
	 */
	public PizzaDaoImpl() {
		String placeholderImg = "http://placehold.it/150x150";
		pizzas = new HashMap<>();
		pizzas.put("PEP", new Pizza(1, "PEP", "Pépéroni", BigDecimal.valueOf(12.50), CategoriePizza.VIANDE, placeholderImg));
		pizzas.put("MAR", new Pizza(2, "MAR", "Margherita", BigDecimal.valueOf(14.00), CategoriePizza.SANS_VIANDE, placeholderImg));
		pizzas.put("REI", new Pizza(3, "REI", "La Reine", BigDecimal.valueOf(11.50), CategoriePizza.VIANDE, placeholderImg));
		pizzas.put("FRO", new Pizza(4, "FRO", "La 4 fromages", BigDecimal.valueOf(12.00), CategoriePizza.SANS_VIANDE, placeholderImg));
		pizzas.put("CAN", new Pizza(5, "CAN", "La cannibale", BigDecimal.valueOf(12.50), CategoriePizza.VIANDE, placeholderImg));
		pizzas.put("SAV", new Pizza(6, "SAV", "La savoyarde", BigDecimal.valueOf(13.00), CategoriePizza.VIANDE, placeholderImg));
		pizzas.put("ORI", new Pizza(7, "ORI", "L'orientale", BigDecimal.valueOf(13.50), CategoriePizza.VIANDE, placeholderImg));
		pizzas.put("IND", new Pizza(8, "IND", "L'indienne", BigDecimal.valueOf(14.00), CategoriePizza.VIANDE, placeholderImg));
		pizzas.put("SAU", new Pizza(9, "SAU", "La saumonetta", BigDecimal.valueOf(15.50), CategoriePizza.POISSON, placeholderImg));
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
		pizzas.put(pizza.getCode(), pizza);
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		if (!pizzas.containsKey(pizza.getCode())) {
			throw new UpdatePizzaException();
		}
		pizzas.put(codePizza, pizza);
	}

	@Override
	public void deletePizza(String codePizza) throws DaoException {
		if (!pizzas.containsKey(codePizza)) {
			throw new DeletePizzaException();
		}
		pizzas.remove(codePizza);
	}

	@Override
	public void importFromFiles(PizzaDaoFichierImpl pizzaDaoFichierImpl, int nb) throws DaoException {
		throw new UnsupportedOperationException("La méthode n'est utilisable uniquement avec les bases de données.");
	}
}
