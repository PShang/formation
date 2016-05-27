package fr.pizzeria.admin.api.rest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
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

import fr.pizzeria.admin.metier.PizzaService;
import fr.pizzeria.admin.web.ListerPizzaController;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Pizza;

@Path("/pizzas")
public class PizzaApi {
	private static final Logger LOG = Logger.getLogger(ListerPizzaController.class.toString());
	@Inject PizzaService pizzaService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pizza> getPizza() {
		return pizzaService.findAllPizzas();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postPizza(Pizza pizza) {
		ResponseBuilder builder;
		try {
			pizzaService.saveNewPizza(pizza);
			builder = Response.status(Status.CREATED);
		} catch (DaoException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			builder = Response.status(Status.INTERNAL_SERVER_ERROR);
		}
		return builder.build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putPizza(Pizza pizza) {
		ResponseBuilder builder;
		try {
			pizzaService.updatePizza(pizza.getCode(), pizza);
			builder = Response.status(Status.OK);
		} catch (DaoException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			builder = Response.status(Status.INTERNAL_SERVER_ERROR);
		}
		return builder.build();
	}

	@GET
	@Path("/{code}")
	public Response deletePizza(@PathParam("code") String code) {
		ResponseBuilder builder;
		try {
			pizzaService.deletePizza(code);
			builder = Response.status(Status.OK);
		} catch (DaoException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			builder = Response.status(Status.INTERNAL_SERVER_ERROR);
		}
		return builder.build();
	}
}
