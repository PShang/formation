package fr.pizzeria.model;

/**
 * La classe de définition d'une pizza.
 */
public class Pizza {
	private String code;
	private String nom;
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
	 */
	public Pizza(String code, String nom, double prix) {
		this.code = code;
		this.nom = nom;
		this.prix = prix;
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
}
