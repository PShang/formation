package fr.pizzeria.admin.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import fr.pizzeria.dao.pizza.IPizzaDao;
import fr.pizzeria.dao.pizza.PizzaDaoJdbcImpl;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * Servlet implementation class NouvellePizzaController
 */
public class NouvellePizzaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IPizzaDao pizzaDao;

	public NouvellePizzaController() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			pizzaDao = new PizzaDaoJdbcImpl("jdbc:mysql://localhost:3306/pizzeria", "root", "");
		} catch (DaoException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/pizzas/nouvellePizza.jsp");
		dispatcher.forward(request, response);
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
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				response.setHeader("Location", request.getContextPath() + "/pizzas/list");
			} catch (DaoException e) {
				response.sendError(500, e.getMessage());
			}
		}
	}
}
