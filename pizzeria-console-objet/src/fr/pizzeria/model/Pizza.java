package fr.pizzeria.model;

/**
 * La classe de définition d'une pizza.
 */
public class Pizza implements Comparable<Pizza> {
	private String code;
	private String nom;
	private double prix;
	private CategoriePizza categorie;
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
		return code + " -> " + nom + " [" + categorie.getLibelle() + "] (" + prix + "€)";
	}

	@Override
	public int compareTo(Pizza o) {
		return this.code.compareTo(o.code);
	}
}
