package fr.pizzeria.admin.api.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ApplicationScoped;

@ApplicationScoped
public class TokenService {
	private static List<String> tokensValides = new ArrayList<>();

	public String generateNewToken() {
		String token = UUID.randomUUID().toString();
		tokensValides.add(token);
		return token;
	}

	public boolean isTokenValid(String token) {
		return tokensValides.contains(token);
	}
}
