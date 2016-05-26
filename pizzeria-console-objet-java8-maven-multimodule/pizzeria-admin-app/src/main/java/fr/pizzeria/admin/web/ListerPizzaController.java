package fr.pizzeria.admin.web;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pizzeria.admin.metier.PizzaService;
import fr.pizzeria.model.Pizza;

/**
 * Servlet implementation class ListerPizzaController
 */
@WebServlet(urlPatterns = { "/pizzas/list" })
public class ListerPizzaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject private PizzaService pizzaService;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Pizza> pizzas = pizzaService.findAllPizzas();
		request.setAttribute("list", pizzas);
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/pizzas/listerPizzas.jsp");
		dispatcher.forward(request, response);
	}
}
