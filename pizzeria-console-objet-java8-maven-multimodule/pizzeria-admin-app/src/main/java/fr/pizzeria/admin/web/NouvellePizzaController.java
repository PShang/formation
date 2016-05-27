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
 * Servlet implementation class NouvellePizzaController
 */
@WebServlet(urlPatterns = { "/pizzas/new" })
public class NouvellePizzaController extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(NouvellePizzaController.class.toString());
	private static final long serialVersionUID = 1L;
	@Inject private PizzaService pizzaService;

	@Override
	public void init() throws ServletException {
		getServletContext().setAttribute("cats", CategoriePizza.values());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/pizzas/nouvellePizza.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		String nom = request.getParameter("nom");
		String prix = request.getParameter("prix");
		String categorie = request.getParameter("categorie");
		String urlImage = request.getParameter("urlImage");
		try {
			if (StringUtils.isBlank(code) || StringUtils.isBlank(nom) || StringUtils.isBlank(prix) || StringUtils.isBlank(categorie)) {
				response.sendError(500, "Tous les champs ne sont pas renseignés.");
			} else {
				Pizza p = new Pizza();
				p.setCode(code);
				p.setNom(nom);
				p.setPrix(new BigDecimal(prix));
				p.setCategorie(CategoriePizza.valueOf(categorie));
				p.setUrlImage(urlImage);
				pizzaService.saveNewPizza(p);
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				response.setHeader("Location", request.getContextPath() + "/pizzas/list");
			}
		} catch (DaoException e) {
			LOG.log(Level.SEVERE, "Erreur de création Pizza", e);
			response.sendError(500, e.getMessage());
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
