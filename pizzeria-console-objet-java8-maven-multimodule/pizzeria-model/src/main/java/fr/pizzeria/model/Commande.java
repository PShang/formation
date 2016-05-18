package fr.pizzeria.model;

import java.util.Calendar;
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
	@Column(nullable = false)
	private Integer id;
	@Column(name = "numero_commande", length = 32, nullable = false, unique = true)
	private String numCommande;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Statut statut;
	@Column(name = "date_commande", nullable = false)
	private Date dateCommande;

	@ManyToOne
	@JoinColumn(name = "livreur_id", nullable = true)
	private Livreur livreur;
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
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

	public Commande(Date dateCommande, Client client, List<Pizza> pizzas) {
		this.numCommande = "CMD_" + Calendar.getInstance().getTimeInMillis();
		this.statut = Statut.NON_TRAITEE;
		this.dateCommande = dateCommande;
		this.client = client;
		this.pizzas = pizzas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumCommande() {
		return numCommande;
	}

	public void setNumCommande(String numCommande) {
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

	@Override
	public String toString() {
		return "Commande numéro : " + numCommande + ", statut : " + statut + ", date : " + dateCommande + ", pizzas : "
				+ pizzas;
	}
}
