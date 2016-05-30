package fr.pizzeria.admin.api.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/login")
public class LoginRessource {
	@Inject private TokenService tokenService;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@FormParam("email") String email, @FormParam("pass") String pass) {
		Response resp;
		if ("admin@pizzeria.fr".equals(email) && "admin".equals(pass)) {
			String token = tokenService.generateNewToken();
			resp = Response.ok(token).build();
		} else {
			resp = Response.status(Status.FORBIDDEN).build();
		}
		return resp;
	}
}
