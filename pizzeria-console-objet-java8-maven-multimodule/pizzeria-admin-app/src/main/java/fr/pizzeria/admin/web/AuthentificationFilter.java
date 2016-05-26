package fr.pizzeria.admin.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthentificationFilter
 */
@WebFilter(urlPatterns = { "/pizzas/*" })
public class AuthentificationFilter implements Filter {

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// Rien à faire
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// Rien à faire
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		Boolean logged = (Boolean) session.getAttribute("logged");
		if (logged != null && logged) {
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/login");
		}
	}

}
