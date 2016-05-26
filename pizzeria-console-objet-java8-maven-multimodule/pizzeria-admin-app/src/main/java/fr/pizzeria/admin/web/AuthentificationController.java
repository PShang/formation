package fr.pizzeria.admin.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AuthentificationController
 */
@WebServlet(urlPatterns = { "/login" })
public class AuthentificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		if ("admin@pizzeria.fr".equals(email) && "admin".equals(pass)) {
			request.getSession().setAttribute("logged", true);
			response.sendRedirect(request.getContextPath() + "/pizzas/list");
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Connexion non authorisé");
		}
	}

}
