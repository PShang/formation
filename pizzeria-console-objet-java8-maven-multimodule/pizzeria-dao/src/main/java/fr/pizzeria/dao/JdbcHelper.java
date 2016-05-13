package fr.pizzeria.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcHelper {
	private String urlConnection;
	private String userConnection;
	private String passConnection;
	private Connection connection;
	private Statement statement;

	public JdbcHelper(String driverConnection, String urlConnection, String userConnection, String passConnection) {
		try {
			Class.forName(driverConnection);
			this.urlConnection = urlConnection;
			this.userConnection = userConnection;
			this.passConnection = passConnection;
		} catch (ClassNotFoundException e) {
			System.err.println("Erreur : Le driver " + driverConnection + " est introuvable.");
		}
	}

	private void open() throws SQLException {
		connection = DriverManager.getConnection(urlConnection, userConnection, passConnection);
	}

	public void close() throws SQLException {
		if (!statement.isClosed()) {
			statement.close();
		}
		if (!connection.isClosed()) {
			connection.close();
		}
	}

	public ResultSet executeQuery(String request) throws SQLException {
		open();
		statement = connection.createStatement();
		return statement.executeQuery(request);
	}

	public int executeUpdate(String request) throws SQLException {
		open();
		statement = connection.createStatement();
		return statement.executeUpdate(request);
	}
}
