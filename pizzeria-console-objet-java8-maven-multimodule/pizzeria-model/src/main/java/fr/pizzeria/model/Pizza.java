package fr.pizzeria.model;

import java.math.BigDecimal;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * La classe de définition d'une pizza.
 */
@Entity
@NamedQueries({ @NamedQuery(name = "pizza.findByCode", query = "SELECT p FROM Pizza p WHERE p.code = :code") })
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
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CategoriePizza categorie;
	@ToString
	@Column(nullable = false)
	private BigDecimal prix;
	@Column(name = "url_image", nullable = true)
	private String urlImage;

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
		super();
		this.urlImage = "http://placehold.it/150x150";
	}

	/**
	 * Constructeur.
	 * 
	 * @param code Le code de la pizza.
	 * @param nom Le nom de la pizza.
	 * @param prix Le prix de la pizza.
	 * @param categorie La catégorie de la pizza.
	 */
	public Pizza(String code, String nom, BigDecimal prix, CategoriePizza categorie) {
		this.code = code;
		this.nom = nom;
		this.prix = prix;
		this.categorie = categorie;
		this.urlImage = "http://placehold.it/150x150";
		Pizza.nbPizzas++;
	}

	/**
	 * Constructeur.
	 * 
	 * @param code Le code de la pizza.
	 * @param nom Le nom de la pizza.
	 * @param prix Le prix de la pizza.
	 * @param categorie La catégorie de la pizza.
	 */
	public Pizza(Integer id, String code, String nom, BigDecimal prix, CategoriePizza categorie, String urlImage) {
		this.id = id;
		this.code = code;
		this.nom = nom;
		this.prix = prix;
		this.categorie = categorie;
		this.urlImage = urlImage;
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

	public CategoriePizza getCategorie() {
		return categorie;
	}

	public BigDecimal getPrix() {
		return prix;
	}

	public String getUrlImage() {
		return urlImage;
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

	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String toJSON() {
		return "{\"id\":\"" + id + "\",\"code\":\"" + code + "\",\"nom\":\"" + nom + "\",\"categorie\":\"" + categorie.toString() + "\",\"prix\":\"" + prix + "\",\"urlImage\":\"" + urlImage + "\"}";
	}

	@Override
	public String toString() {
		return Arrays.asList(getClass().getDeclaredFields()).stream().filter(f -> f.getAnnotation(ToString.class) != null).map(f -> {
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
