package fr.pizzeria.admin.web;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import fr.pizzeria.dao.pizza.IPizzaDao;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * Servlet implementation class EditerPizzaController
 */
public class EditerPizzaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IPizzaDao pizzaDao = IPizzaDao.DEFAULT_IMPLEMENTATION;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		try {
			request.setAttribute("pizza", pizzaDao.getPizza(code));
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/pizzas/editerPizza.jsp");
			dispatcher.forward(request, response);
		} catch (DaoException e) {
			response.sendError(500, e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		String nom = request.getParameter("nom");
		String prix = request.getParameter("prix");
		String categorie = request.getParameter("categorie");
		String urlImage = request.getParameter("urlImage");
		if (StringUtils.isBlank(code) || StringUtils.isBlank(nom) || StringUtils.isBlank(prix) || StringUtils.isBlank(categorie)) {
			response.sendError(500, "Tous les champs ne sont pas renseignés.");
		} else {
			try {
				Pizza p = pizzaDao.getPizza(code);
				p.setNom(nom);
				p.setPrix(new BigDecimal(prix));
				p.setCategorie(CategoriePizza.valueOf(categorie));
				p.setUrlImage(urlImage);
				pizzaDao.updatePizza(code, p);
				response.sendRedirect(request.getContextPath() + "/pizzas/list");
			} catch (DaoException e) {
				response.sendError(500, e.getMessage());
			}
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		if (code == null || StringUtils.isBlank(code)) {
			response.sendError(500, "Le code est vide.");
		} else {
			try {
				pizzaDao.deletePizza(code);
			} catch (DaoException e) {
				response.sendError(500, e.getMessage());
			}
		}
	}
}
