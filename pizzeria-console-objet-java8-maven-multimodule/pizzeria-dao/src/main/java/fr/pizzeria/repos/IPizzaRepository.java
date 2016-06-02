package fr.pizzeria.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.pizzeria.model.Pizza;

public interface IPizzaRepository extends JpaRepository<Pizza, Integer> {

	Pizza findOneByCode(String code);

	Integer deleteByCode(String codePizza);
}
