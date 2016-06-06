package fr.pizzeria.spring.mvc.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.pizzeria.dao.pizza.IPizzaDao;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

@Controller
@RequestMapping("/pizzas")
@Secured({ "ROLE_USER", "ROLE_ADMIN" })
public class PizzaController {

	@Autowired private IPizzaDao pizzaDao;

	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model) {
		List<Pizza> list = pizzaDao.findAllPizzas();
		model.addAttribute("list", list);
		model.addAttribute("cats", CategoriePizza.values());
		return "pizzas";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String post(@RequestParam("code") String code, @RequestParam("nom") String nom, @RequestParam("categorie") String categorie, @RequestParam("prix") BigDecimal prix,
			@RequestParam("urlImage") String urlImage) throws DaoException {
		pizzaDao.saveNewPizza(new Pizza(null, code, nom, prix, CategoriePizza.valueOf(categorie), urlImage));
		return "redirect:/api/pizzas";
	}

	@RequestMapping(path = "/delete/{code}", method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.OK)
	public String delete(@PathVariable("code") String code) throws DaoException {
		pizzaDao.deletePizza(code);
		return "redirect:/api/pizzas";
	}
}
