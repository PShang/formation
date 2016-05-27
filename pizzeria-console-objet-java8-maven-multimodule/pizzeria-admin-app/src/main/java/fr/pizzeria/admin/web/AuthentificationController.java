package fr.pizzeria.admin.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private static final Logger LOG = Logger.getLogger(AuthentificationController.class.toString());
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			LOG.log(Level.SEVERE, "Erreur : " + e.getMessage(), e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		try {
			if ("admin@pizzeria.fr".equals(email) && "admin".equals(pass)) {
				request.getSession().setAttribute("logged", true);
				response.sendRedirect(request.getContextPath() + "/pizzas/list");
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Connexion non authorisé");
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Erreur : " + e.getMessage(), e);
		}
	}

}
