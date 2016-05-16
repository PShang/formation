package fr.pizzeria.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * La classe de définition d'une pizza.
 */
@Entity
public class Pizza {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer id;
	@ToString(uppercase = true)
	@Column(length = 8, nullable = false, unique = true)
	private String code;
	@ToString
	@Column(length = 32, nullable = false)
	private String nom;
	@ToString
	@Column(nullable = false)
	private double prix;
	@ToString
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CategoriePizza categorie;
	/**
	 * Variable statique servant a compter la création des pizzas..
	 */
	public static int nbPizzas;

	private static final Map<String, String> FORMAT = new HashMap<>();

	static {
		FORMAT.put("code", "%s -> ");
		FORMAT.put("nom", "%s");
		FORMAT.put("categorie", " [%s]");
		FORMAT.put("prix", " (%s€)");
	}

	/**
	 * Constructeur vide pour JPA.
	 */
	public Pizza() {
	}

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

	public Integer getId() {
		return id;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setCategorie(CategoriePizza categorie) {
		this.categorie = categorie;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	@Override
	public String toString() {
		return Arrays.asList(getClass().getDeclaredFields()).stream()
				.filter(f -> f.getAnnotation(ToString.class) != null).map(f -> {
					ToString ts = f.getAnnotation(ToString.class);
					try {
						String s;
						if ("categorie".equals(f.getName()))
							s = ((CategoriePizza) f.get(this)).getLibelle().toString();
						else
							s = f.get(this).toString();
						return String.format(FORMAT.get(f.getName()), ts.uppercase() ? s.toUpperCase() : s);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
						return "";
					}
				}).collect(Collectors.joining());
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
