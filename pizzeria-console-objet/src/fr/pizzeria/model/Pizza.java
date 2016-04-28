package fr.pizzeria.model;

public class Pizza {
	private String code;
	private String nom;
	private double prix;
	static public int nbPizzas;

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
