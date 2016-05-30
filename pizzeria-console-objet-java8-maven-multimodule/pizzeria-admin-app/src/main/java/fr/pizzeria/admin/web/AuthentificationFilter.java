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
@WebFilter(urlPatterns = { "/*" })
public class AuthentificationFilter implements Filter {

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// Rien à faire
	}

	@Override
	public void destroy() {
		// Rien à faire
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		Boolean logged = (Boolean) session.getAttribute("logged");
		if ((logged != null && logged) || httpRequest.getRequestURI().contains("/login") || httpRequest.getRequestURI().contains("/api/rest")) {
			chain.doFilter(request, response);
		} else {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
		}
	}

}
