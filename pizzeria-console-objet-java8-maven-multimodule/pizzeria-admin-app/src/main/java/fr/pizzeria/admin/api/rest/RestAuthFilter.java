package fr.pizzeria.admin.api.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class RestAuthFilter implements ContainerRequestFilter {
	@Inject private TokenService tokenService;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String token = requestContext.getHeaderString("auth");
		if (!tokenService.isTokenValid(token) && !requestContext.getUriInfo().getPath().contains("/login")) {
			requestContext.abortWith(Response.status(Status.FORBIDDEN).build());
		}
	}
}
