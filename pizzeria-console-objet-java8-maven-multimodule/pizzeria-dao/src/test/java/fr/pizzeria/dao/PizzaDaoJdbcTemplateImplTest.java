package fr.pizzeria.dao;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

public class PizzaDaoJdbcTemplateImplTest extends PizzaDaoTest {

	@Autowired
	public void setDaoFactory(IDaoFactory daoFactory) {
		Locale.setDefault(Locale.FRENCH);
		dao = daoFactory;
	}
}
