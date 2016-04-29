package fr.pizzeria.model;

import java.lang.reflect.Field;

/**
 * La classe de définition d'une pizza.
 */
public class Pizza implements Comparable<Pizza> {
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

	@Override
	public String toString() {
		String codeString = new String();
		String nomString = new String();
		String categorieString = new String();
		String prixString = new String();
		for (Field field : getClass().getDeclaredFields()) {
			ToString ts;
			if ((ts = field.getAnnotation(ToString.class)) != null) {
				if (field.getName().equals("code")) {
					codeString = (ts.uppercase()) ? (code.toUpperCase() + " -> ") : (code + " -> ");
				} else if (field.getName().equals("nom")) {
					nomString = (ts.uppercase()) ? (nom.toUpperCase()) : (nom);
				} else if (field.getName().equals("categorie")) {
					categorieString = (ts.uppercase()) ? (" [" + categorie.getLibelle().toUpperCase() + "]")
							: (" [" + categorie.getLibelle() + "]");
				} else if (field.getName().equals("prix")) {
					prixString = " (" + prix + "€)";
				}
			}
		}
		return codeString + nomString + categorieString + prixString;
	}

	@Override
	public int compareTo(Pizza o) {
		return this.code.compareTo(o.code);
	}
}
