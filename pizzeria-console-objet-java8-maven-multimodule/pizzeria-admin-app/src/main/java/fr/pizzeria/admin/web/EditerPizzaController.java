package fr.pizzeria.admin.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import fr.pizzeria.admin.metier.PizzaService;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

/**
 * Servlet implementation class EditerPizzaController
 */
@WebServlet(urlPatterns = { "/pizzas/edit" })
public class EditerPizzaController extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(EditerPizzaController.class.toString());
	private static final long serialVersionUID = 1L;
	@Inject private PizzaService pizzaService;

	@Override
	public void init() throws ServletException {
		getServletContext().setAttribute("cats", CategoriePizza.values());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		try {
			request.setAttribute("pizza", pizzaService.getPizza(code));
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/pizzas/editerPizza.jsp");
			dispatcher.forward(request, response);
		} catch (DaoException e) {
			LOG.log(Level.SEVERE, "Erreur de lecture Pizza", e);
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
				Pizza p = pizzaService.getPizza(code);
				p.setNom(nom);
				p.setPrix(new BigDecimal(prix));
				p.setCategorie(CategoriePizza.valueOf(categorie));
				p.setUrlImage(urlImage);
				pizzaService.updatePizza(code, p);
				response.sendRedirect(request.getContextPath() + "/pizzas/list");
			} catch (DaoException e) {
				LOG.log(Level.SEVERE, "Erreur de modification Pizza", e);
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
				pizzaService.deletePizza(code);
			} catch (DaoException e) {
				LOG.log(Level.SEVERE, "Erreur de suppression Pizza", e);
				response.sendError(500, e.getMessage());
			}
		}
	}
}
