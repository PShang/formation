package fr.pizzeria.admin.api.rest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import fr.pizzeria.admin.metier.ClientService;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Client;

@Path("/clients")
public class ClientRessource {
	private static final Logger LOG = Logger.getLogger(ClientRessource.class.toString());
	@Inject ClientService clientService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Client> getClient() {
		return clientService.findAllClients();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postPizza(Client client) {
		ResponseBuilder builder;
		try {
			clientService.saveNewClient(client);
			builder = Response.status(Status.CREATED);
		} catch (DaoException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			builder = Response.status(Status.INTERNAL_SERVER_ERROR);
		}
		return builder.build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putPizza(Client client) {
		ResponseBuilder builder;
		try {
			clientService.updateClient(client.getId(), client);
			builder = Response.status(Status.OK);
		} catch (DaoException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			builder = Response.status(Status.INTERNAL_SERVER_ERROR);
		}
		return builder.build();
	}

	@DELETE
	@Path("/{id}")
	public Response deletePizza(@PathParam("id") Integer id) {
		ResponseBuilder builder;
		try {
			clientService.deleteClient(id);
			builder = Response.status(Status.OK);
		} catch (DaoException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			builder = Response.status(Status.INTERNAL_SERVER_ERROR);
		}
		return builder.build();
	}
}
