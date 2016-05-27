package fr.pizzeria.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * La classe de définition d'un client.
 */
@Entity
@NamedQueries({ @NamedQuery(name = "client.findByEmail", query = "SELECT c FROM Client c WHERE c.email = :email") })
public class Client {
	@ToString @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(nullable = false) private Integer id;
	@ToString @Column(length = 32, nullable = false) private String nom;
	@ToString @Column(length = 32, nullable = false) private String prenom;
	@ToString @Column(length = 64, nullable = false, unique = true) private String email;
	@Column(name = "mot_de_passe", length = 32, nullable = false) private String mdp;

	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER) private Set<Commande> commandes;

	private static final Map<String, String> FORMAT = new HashMap<>();

	static {
		FORMAT.put("id", "%s -> ");
		FORMAT.put("nom", "%s");
		FORMAT.put("prenom", " %s");
		FORMAT.put("email", " (%s)");
	}

	/**
	 * Constructeur vide pour JPA.
	 */
	public Client() {
		super();
	}

	/**
	 * Constructeur.
	 * 
	 * @param nom Le nom du client.
	 * @param prenom Le prénom du client.
	 * @param email L'e-mail du client.
	 * @param mdp Le mot de passe du client.
	 */
	public Client(String nom, String prenom, String email, String mdp) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mdp = mdp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public Set<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(Set<Commande> commandes) {
		this.commandes = commandes;
	}

	@Override
	public String toString() {
		return Arrays.asList(getClass().getDeclaredFields()).stream().filter(f -> f.getAnnotation(ToString.class) != null).map(f -> {
			ToString ts = f.getAnnotation(ToString.class);
			try {
				String s = f.get(this).toString();
				return String.format(FORMAT.get(f.getName()), ts.uppercase() ? s.toUpperCase() : s);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return "";
			}
		}).collect(Collectors.joining());
	}
}
