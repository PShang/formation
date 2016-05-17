package fr.pizzeria.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 32)
	private String nom;
	@Column(length = 32)
	private String prenom;
	@Column(length = 64, unique = true)
	private String email;
	@Column(name = "mot_de_passe", length = 32)
	private String mdp;

	@OneToMany(mappedBy = "client")
	public Set<Commande> commandes;

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
}
