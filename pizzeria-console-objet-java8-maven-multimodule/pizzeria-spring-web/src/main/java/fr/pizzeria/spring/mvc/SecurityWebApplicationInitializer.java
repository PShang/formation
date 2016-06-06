package fr.pizzeria.spring.mvc;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import fr.pizzeria.spring.mvc.config.PizzeriaSecurityConfig;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	public SecurityWebApplicationInitializer() {
		super(PizzeriaSecurityConfig.class);
	}
}
