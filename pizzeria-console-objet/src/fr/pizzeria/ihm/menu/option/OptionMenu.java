package fr.pizzeria.ihm.menu.option;

public abstract class OptionMenu {

	private String libelle;

	public OptionMenu(String libelle) {
		this.libelle = libelle;
	}

	public abstract boolean execute();

	public String getLibelle() {
		return libelle;
	}
}
