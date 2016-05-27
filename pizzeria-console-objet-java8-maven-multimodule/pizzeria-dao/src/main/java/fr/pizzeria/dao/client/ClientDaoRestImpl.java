package fr.pizzeria.dao.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Client;

public class ClientDaoRestImpl implements IClientDao {
	private static final String CLIENT_URL = "/clients";
	private static final String CLIENT_FIELD_ID = "id";
	private static final String CLIENT_FIELD_NOM = "nom";
	private static final String CLIENT_FIELD_PRENOM = "prenom";
	private static final String CLIENT_FIELD_EMAIL = "email";
	private static final String CLIENT_FIELD_MDP = "mdp";
	private String baseUrl;

	public ClientDaoRestImpl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public List<Client> findAllClients() {
		List<Client> clients = new ArrayList<>();
		try {
			URL url = new URL(baseUrl + CLIENT_URL);
			InputStream response = url.openStream();
			JsonReader jsonReader = Json.createReader(response);
			JsonArray array = jsonReader.readArray();
			array.forEach(val -> {
				JsonObject obj = (JsonObject) val;
				Client c = new Client();
				c.setId(obj.getInt(CLIENT_FIELD_ID));
				c.setNom(obj.getString(CLIENT_FIELD_NOM));
				c.setPrenom(obj.getString(CLIENT_FIELD_PRENOM));
				c.setEmail(obj.getString(CLIENT_FIELD_EMAIL));
				c.setMdp(obj.getString(CLIENT_FIELD_MDP));
				clients.add(c);
			});
			jsonReader.close();
		} catch (MalformedURLException e) {
			System.err.println("Erreur dans l'URL de l'API REST lors de la lecture des clients : " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Erreur IO lors de la lecture des clients : " + e.getMessage());
		}
		return clients;
	}

	@Override
	public Client getClient(String email, String mdp) throws DaoException {
		throw new UnsupportedOperationException("La méthode n'est pas disponible en REST.");
	}

	@Override
	public void saveNewClient(Client client) throws DaoException {
		String json = Json.createObjectBuilder().add(CLIENT_FIELD_NOM, client.getNom()).add(CLIENT_FIELD_PRENOM, client.getPrenom()).add(CLIENT_FIELD_EMAIL, client.getEmail())
				.add(CLIENT_FIELD_MDP, client.getMdp()).build().toString();
		try {
			URL url = new URL(baseUrl + CLIENT_URL);
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
			throw new DaoException("Erreur dans l'URL de l'API REST lors de la création d'un client.", e);
		} catch (IOException e) {
			throw new DaoException("Erreur IO lors de la création d'un client.", e);
		}
	}

	@Override
	public void updateClient(Integer idClient, Client client) throws DaoException {
		String json = Json.createObjectBuilder().add(CLIENT_FIELD_NOM, client.getNom()).add(CLIENT_FIELD_PRENOM, client.getPrenom()).add(CLIENT_FIELD_EMAIL, client.getEmail())
				.add(CLIENT_FIELD_MDP, client.getMdp()).build().toString();
		try {
			URL url = new URL(baseUrl + CLIENT_URL);
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
			throw new DaoException("Erreur dans l'URL de l'API REST lors de la modification d'un client.", e);
		} catch (IOException e) {
			throw new DaoException("Erreur IO lors de la modification d'un client.", e);
		}
	}

	@Override
	public void deleteClient(Integer idClient) throws DaoException {
		try {
			URL url = new URL(baseUrl + CLIENT_URL + "/" + idClient);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			InputStream response = connection.getInputStream();
			response.close();
		} catch (MalformedURLException e) {
			throw new DaoException("Erreur dans l'URL de l'API REST lors de la suppression d'un client.", e);
		} catch (IOException e) {
			throw new DaoException("Erreur IO lors de la suppression d'un client.", e);
		}
	}
}
