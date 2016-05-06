package fr.pizzeria.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * La classe de définition d'une pizza.
 */
public class Pizza /* implements Comparable<Pizza> */ {
	@ToString
	private String code;
	@ToString(uppercase = true)
	private String nom;
	@ToString
	private CategoriePizza categorie;
	@ToString
	private double prix;
	/**
	 * Variable statique servant a compter la création des pizzas..
	 */
	static public int nbPizzas;

	/**
	 * Constructeur.
	 * 
	 * @param code Le code de la pizza.
	 * @param nom Le nom de la pizza.
	 * @param prix Le prix de la pizza.
	 * @param categorie La catégorie de la pizza.
	 */
	public Pizza(String code, String nom, double prix, CategoriePizza categorie) {
		this.code = code;
		this.nom = nom;
		this.prix = prix;
		this.categorie = categorie;
		Pizza.nbPizzas++;
	}

	public String getCode() {
		return code;
	}

	public String getNom() {
		return nom;
	}

	public double getPrix() {
		return prix;
	}

	public CategoriePizza getCategorie() {
		return categorie;
	}

	private static Map<String, String> FORMAT = new HashMap<>();

	static {
		FORMAT.put("code", "%s -> ");
		FORMAT.put("nom", "%s");
		FORMAT.put("categorie", " [%s]");
		FORMAT.put("prix", " (%s€)");
	}

	@Override
	public String toString() {
		return Arrays.asList(getClass().getDeclaredFields()).stream()
				.filter(f -> f.getAnnotation(ToString.class) != null).map(f -> {
					ToString ts = f.getAnnotation(ToString.class);
					try {
						String s;
						if (f.getName().equals("categorie"))
							s = ((CategoriePizza) f.get(this)).getLibelle().toString();
						else
							s = f.get(this).toString();
						return String.format(FORMAT.get(f.getName()), (ts.uppercase()) ? (s.toUpperCase()) : (s));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
						return "";
					}
				}).collect(Collectors.joining());
	}
}
