package fr.pizzeria.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * La classe de définition d'une pizza.
 */
public class Pizza /* implements Comparable<Pizza> */ {
	@ToString(uppercase = true)
	private String code;
	@ToString
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categorie == null) ? 0 : categorie.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		long temp;
		temp = Double.doubleToLongBits(prix);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pizza other = (Pizza) obj;
		if (categorie != other.categorie) {
			return false;
		}
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (nom == null) {
			if (other.nom != null) {
				return false;
			}
		} else if (!nom.equals(other.nom)) {
			return false;
		}
		if (Double.doubleToLongBits(prix) != Double.doubleToLongBits(other.prix)) {
			return false;
		}
		return true;
	}
}
