package fr.pizzeria.admin.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import fr.pizzeria.dao.pizza.IPizzaDao;
import fr.pizzeria.dao.pizza.PizzaDaoImpl;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * Servlet implementation class PizzaServletWebApi
 */
public class PizzaServletWebApi extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(PizzaServletWebApi.class.toString());
	private IPizzaDao pizzaDao = new PizzaDaoImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Pizza> pizzas = pizzaDao.findAllPizzas();
			List<String> jsonList = new ArrayList<>();
			for (Pizza pizza : pizzas) {
				jsonList.add(pizza.toJSON());
			}
			String json = "[" + String.join(",", jsonList) + "]";
			response.getWriter().write(json);
		} catch (Exception e) {
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		String nom = request.getParameter("nom");
		String prix = request.getParameter("prix");
		String categorie = request.getParameter("categorie");
		String urlImage = request.getParameter("urlImage");

		if (StringUtils.isBlank(code) || StringUtils.isBlank(nom) || StringUtils.isBlank(prix) || StringUtils.isBlank(categorie) || StringUtils.isBlank(urlImage)) {
			response.sendError(500, "Tous les champs ne sont pas renseignés.");
		} else {
			try {
				Pizza p = new Pizza();
				p.setCode(code);
				p.setNom(nom);
				p.setPrix(new BigDecimal(prix));
				p.setCategorie(CategoriePizza.valueOf(categorie));
				p.setUrlImage(urlImage);
				pizzaDao.saveNewPizza(p);
				LOG.info("J'ai bien reçu le POST avec le code = " + code + ", nom = " + nom + ", prix = " + prix + ", categorie = " + categorie);
				response.setStatus(201);
			} catch (DaoException e) {
				response.sendError(500, e.getMessage());
			}
		}
	}
}
