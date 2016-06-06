package fr.pizzeria.spring.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.pizzeria.dao.performance.IPerformanceDao;
import fr.pizzeria.exception.DaoException;
import fr.pizzeria.model.Performance;

@Controller
@RequestMapping("/performance")
@Secured("ROLE_ADMIN")
public class PerformanceController {

	@Autowired private IPerformanceDao performanceDao;

	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model) {
		List<Performance> list = performanceDao.findAllPerformances();
		model.addAttribute("list", list);
		return "performance";
	}

	@RequestMapping(path = "/deleteall", method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.OK)
	public String delete() throws DaoException {
		performanceDao.deleteAllPerformances();
		return "redirect:/api/performance";
	}

	@RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.OK)
	public String delete(@PathVariable("id") int id) throws DaoException {
		performanceDao.deletePerformance(id);
		return "redirect:/api/performance";
	}
}
