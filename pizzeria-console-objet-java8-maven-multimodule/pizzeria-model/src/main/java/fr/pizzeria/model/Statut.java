package fr.pizzeria.model;

/**
 * Enumeration pour désigner le statut d'une commande.
 */
public enum Statut {
	NON_TRAITEE("Non traitée"), PREPARATION("En préparation"), LIVRAISON("En livraison"), TERMINEE("Terminée");

	private String libelle;

	private Statut(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
