package fr.pizzeria.dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * Implémentation de la DAO utilisant une {@link Map} pour les pizzas.
 */
public class PizzaDaoFichierImpl implements IPizzaDao {

	/**
	 * La {@link Map} pour les pizzas.
	 */
	private Map<String, Pizza> pizzas = new HashMap<String, Pizza>();;

	private final static Path ROOTDIR = Paths.get("data");

	/**
	 * Constructeur. Initialise la {@link Map} de pizzas en lisant les fichiers
	 * de données des pizzas.
	 * 
	 * @throws DaoException
	 */
	public PizzaDaoFichierImpl() throws DaoException {
		try {
			if (Files.notExists(ROOTDIR)) {
				Files.createDirectory(ROOTDIR);
			}
			Files.list(ROOTDIR).map(path -> {
				try {
					String[] line = Files.readAllLines(path, StandardCharsets.ISO_8859_1).get(0).split(";");
					return new Pizza(path.getFileName().toString().replace(".txt", ""), line[0],
							Double.parseDouble(line[1]), CategoriePizza.valueOf(line[2]));
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}).forEach(p -> {
				pizzas.put(p.getCode(), p);
			});
		} catch (IOException e) {
			throw new DaoException("Erreur de lecture des fichiers de donnée des pizzas", e);
		}
	}

	private void writePizzaFile(Pizza pizza) throws DaoException {
		try {
			byte[] datas = String.format("%s;%d;%s", pizza.getNom(), pizza.getPrix(), pizza.getCategorie().toString())
					.getBytes();
			Files.write(Paths.get(ROOTDIR.toString(), pizza.getCode() + ".txt"), datas, StandardOpenOption.CREATE_NEW);
		} catch (IOException e) {
			throw new DaoException("Erreur dans l'écriture des fichiers de donnée des pizzas", e);
		}
	}

	private void deletePizzaFile(String codePizza) throws DaoException {
		try {
			Files.delete(Paths.get(ROOTDIR.toString(), codePizza + ".txt"));
		} catch (IOException e) {
			throw new DaoException("Erreur dans la suppréssion d'un fichier de donnée des pizzas", e);
		}
	}

	@Override
	public List<Pizza> findAllPizzas() {
		return pizzas.values().stream().sorted(Comparator.comparing(Pizza::getNom)).collect(Collectors.toList());
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws DaoException {
		if (pizzas.containsKey(pizza.getCode())) {
			throw new DaoException();
		}
		pizzas.put(pizza.getCode(), pizza);
		writePizzaFile(pizza);
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		if (!pizzas.containsKey(pizza.getCode())) {
			throw new DaoException();
		}
		pizzas.put(codePizza, pizza);
		writePizzaFile(pizza);
	}

	@Override
	public void deletePizza(String codePizza) throws DaoException {
		if (!pizzas.containsKey(codePizza)) {
			throw new DaoException();
		}
		pizzas.remove(codePizza);
		deletePizzaFile(codePizza);
	}
}
