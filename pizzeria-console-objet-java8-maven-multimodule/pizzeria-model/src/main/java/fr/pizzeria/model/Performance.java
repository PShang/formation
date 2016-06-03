package fr.pizzeria.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * La classe de définition d'une performance.
 */
@Entity
public class Performance {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(nullable = false) private Integer id;
	@Column(nullable = false) private String service;
	@Column(nullable = false) private Date date;
	@Column(nullable = false) private long tempsExecution;

	/**
	 * Constructeur vide pour JPA.
	 */
	public Performance() {
		super();
	}

	/**
	 * Constructeur.
	 * 
	 * @param id L'id.
	 * @param service Le nom du service
	 * @param date La date/heure de la mesure
	 * @param tempsExecution Le temps d'exécution en ms
	 */
	public Performance(String service, Date date, long tempsExecution) {
		this.service = service;
		this.date = date;
		this.tempsExecution = tempsExecution;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getTempsExecution() {
		return tempsExecution;
	}

	public void setTempsExecution(long tempsExecution) {
		this.tempsExecution = tempsExecution;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "id");
	}
}
