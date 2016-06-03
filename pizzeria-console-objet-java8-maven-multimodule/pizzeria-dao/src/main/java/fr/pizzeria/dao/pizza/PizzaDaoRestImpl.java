package fr.pizzeria.dao.pizza;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * Implémentation de la DAO REST pour les pizzas.
 */
@Repository
@Lazy
public class PizzaDaoRestImpl implements IPizzaDao {
	private static final String PIZZA_URL = "/pizzas";
	private static final String PIZZA_FIELD_ID = "id";
	private static final String PIZZA_FIELD_CODE = "code";
	private static final String PIZZA_FIELD_NOM = "nom";
	private static final String PIZZA_FIELD_CATEGORIE = "categorie";
	private static final String PIZZA_FIELD_PRIX = "prix";
	private static final String PIZZA_FIELD_URL_IMAGE = "urlImage";
	private String baseUrl;

	public PizzaDaoRestImpl() {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Création du bean " + this.getClass().getName());
	}

	@Value("${rest.baseUrl}")
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public List<Pizza> findAllPizzas() {
		List<Pizza> pizzas = new ArrayList<>();
		try {
			URL url = new URL(baseUrl + PIZZA_URL);
			InputStream response = url.openStream();
			JsonReader jsonReader = Json.createReader(response);
			JsonArray array = jsonReader.readArray();
			array.forEach(val -> {
				JsonObject obj = (JsonObject) val;
				Pizza p = new Pizza();
				p.setId(obj.getInt(PIZZA_FIELD_ID));
				p.setCode(obj.getString(PIZZA_FIELD_CODE));
				p.setNom(obj.getString(PIZZA_FIELD_NOM));
				p.setCategorie(CategoriePizza.valueOf(obj.getString(PIZZA_FIELD_CATEGORIE)));
				p.setPrix(new BigDecimal(obj.getJsonNumber(PIZZA_FIELD_PRIX).toString()));
				p.setUrlImage(obj.getString(PIZZA_FIELD_URL_IMAGE));
				pizzas.add(p);
			});
			jsonReader.close();
		} catch (MalformedURLException e) {
			Logger.getGlobal().log(Level.SEVERE, "Erreur dans l'URL de l'API REST lors de la lecture des pizzas : " + e.getMessage(), e);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Erreur IO lors de la lecture des pizzas : " + e.getMessage(), e);
		}
		return pizzas;
	}

	@Override
	public Pizza getPizza(String code) throws DaoException {
		throw new UnsupportedOperationException("La méthode n'est pas disponible en REST.");
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws DaoException {
		String json = Json.createObjectBuilder().add(PIZZA_FIELD_CODE, pizza.getCode()).add(PIZZA_FIELD_NOM, pizza.getNom()).add(PIZZA_FIELD_CATEGORIE, pizza.getCategorie().toString())
				.add(PIZZA_FIELD_PRIX, pizza.getPrix()).add(PIZZA_FIELD_URL_IMAGE, pizza.getUrlImage()).build().toString();
		try {
			URL url = new URL(baseUrl + PIZZA_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			try (OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream())) {
				wr.write(json);
			}
			InputStream response = connection.getInputStream();
			response.close();
		} catch (MalformedURLException e) {
			throw new DaoException("Erreur dans l'URL de l'API REST lors de la création d'une pizza.", e);
		} catch (IOException e) {
			throw new DaoException("Erreur IO lors de la création d'une pizza.", e);
		}
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws DaoException {
		String json = Json.createObjectBuilder().add(PIZZA_FIELD_CODE, pizza.getCode()).add(PIZZA_FIELD_NOM, pizza.getNom()).add(PIZZA_FIELD_CATEGORIE, pizza.getCategorie().toString())
				.add(PIZZA_FIELD_PRIX, pizza.getPrix()).add(PIZZA_FIELD_URL_IMAGE, pizza.getUrlImage()).build().toString();
		try {
			URL url = new URL(baseUrl + PIZZA_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			try (OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream())) {
				wr.write(json);
			}
			InputStream response = connection.getInputStream();
			response.close();
		} catch (MalformedURLException e) {
			throw new DaoException("Erreur dans l'URL de l'API REST lors de la modification d'une pizza.", e);
		} catch (IOException e) {
			throw new DaoException("Erreur IO lors de la modification d'une pizza.", e);
		}
	}

	@Override
	public void deletePizza(String codePizza) throws DaoException {
		try {
			URL url = new URL(baseUrl + PIZZA_URL + "/" + codePizza);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			InputStream response = connection.getInputStream();
			response.close();
		} catch (MalformedURLException e) {
			throw new DaoException("Erreur dans l'URL de l'API REST lors de la suppression d'une pizza.", e);
		} catch (IOException e) {
			throw new DaoException("Erreur IO lors de la suppression d'une pizza.", e);
		}
	}

	@Override
	public void saveAllPizzas(List<Pizza> pizzas, int nb) throws DaoException {
		throw new UnsupportedOperationException("La méthode n'est pas disponible en REST.");
	}
}
