package fr.pizzeria.dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * Implémentation de la DAO utilisant une {@link Map} pour les pizzas.
 */
public class PizzaDaoFichierImpl implements IPizzaDao {

	/**
	 * La {@link Map} pour les pizzas.
	 */
	private Map<String, Pizza> pizzas = new HashMap<>();

	private static final Path ROOTDIR = Paths.get("data");

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
			try (Stream<Path> s = Files.list(ROOTDIR)) {
				s.map(path -> {
					try {
						String[] line = Files.readAllLines(path, StandardCharsets.UTF_8).get(0).split(";");
						NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
						Number number = format.parse(line[1]);
						return new Pizza(path.getFileName().toString().replace(".txt", ""), line[0],
								number.doubleValue(), CategoriePizza.valueOf(line[2]));
					} catch (IOException | ParseException e) {
						e.printStackTrace();
						return null;
					}
				}).forEach(p -> pizzas.put(p.getCode(), p));
			}
		} catch (IOException e) {
			throw new DaoException("Erreur de lecture des fichiers de donnée des pizzas", e);
		}
	}

	@Override
	public void close() {
		throw new UnsupportedOperationException();
	}

	private void writePizzaFile(Pizza pizza) throws DaoException {
		try {
			byte[] datas = String.format("%s;%f;%s", pizza.getNom(), pizza.getPrix(), pizza.getCategorie().toString())
					.getBytes();
			Files.write(Paths.get(ROOTDIR.toString(), pizza.getCode() + ".txt"), datas, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
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
			throw new SavePizzaException();
		}
		pizzas.put(pizza.getCode(), pizza);
		writePizzaFile(pizza);
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		if (!pizzas.containsKey(pizza.getCode())) {
			throw new UpdatePizzaException();
		}
		pizzas.put(codePizza, pizza);
		writePizzaFile(pizza);
	}

	@Override
	public void deletePizza(String codePizza) throws DaoException {
		if (!pizzas.containsKey(codePizza)) {
			throw new DeletePizzaException();
		}
		pizzas.remove(codePizza);
		deletePizzaFile(codePizza);
	}

	@Override
	public void importFromFiles(PizzaDaoFichierImpl pizzaDaoFichierImpl, int nb) throws DaoException {
		throw new UnsupportedOperationException("La méthode n'est utilisable uniquement avec les bases de données.");
	}
}
