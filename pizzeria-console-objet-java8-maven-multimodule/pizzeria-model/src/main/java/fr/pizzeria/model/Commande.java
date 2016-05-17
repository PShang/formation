package fr.pizzeria.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * La classe de définition d'une commande.
 */
@Entity
public class Commande {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "numero_commande", unique = true)
	private Integer numCommande;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Statut statut;
	@Column(name = "date_commande")
	private Date dateCommande;

	@ManyToOne
	@JoinColumn(name = "livreur_id")
	private Livreur livreur;
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	@ManyToMany
	@JoinTable(name = "commande_pizza", joinColumns = @JoinColumn(name = "commande_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "pizza_id", referencedColumnName = "id"))
	public List<Pizza> pizzas;

	/**
	 * Constructeur vide pour JPA.
	 */
	public Commande() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumCommande() {
		return numCommande;
	}

	public void setNumCommande(Integer numCommande) {
		this.numCommande = numCommande;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	public Livreur getLivreur() {
		return livreur;
	}

	public void setLivreur(Livreur livreur) {
		this.livreur = livreur;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
