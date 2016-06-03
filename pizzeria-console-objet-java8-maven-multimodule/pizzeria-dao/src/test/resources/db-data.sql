INSERT INTO `pizza` (`id`, `categorie`, `code`, `nom`, `prix`, `url_image`) VALUES
(1, 'VIANDE', 'ZZZ', 'Aaa', '3.21', 'http://placehold.it/150x150'),
(2, 'VIANDE', 'IND', 'L''indienne', '14.00', 'http://placehold.it/150x150'),
(3, 'VIANDE', 'ORI', 'L''orientale', '13.50', 'http://placehold.it/150x150'),
(4, 'SANS_VIANDE', 'FRO', 'La 4 fromages', '12.00', 'http://placehold.it/150x150'),
(5, 'VIANDE', 'REI', 'La Reine', '11.50', 'http://placehold.it/150x150'),
(6, 'VIANDE', 'CAN', 'La cannibale', '12.50', 'http://placehold.it/150x150'),
(7, 'POISSON', 'SAU', 'La saumonetta', '15.50', 'http://placehold.it/150x150'),
(8, 'VIANDE', 'SAV', 'La savoyarde', '13.00', 'http://placehold.it/150x150'),
(9, 'SANS_VIANDE', 'MAR', 'Margherita', '14.00', 'http://placehold.it/150x150'),
(10, 'VIANDE', 'PEP', 'Pépéroni', '12.50', 'http://placehold.it/150x150'),
(11, 'POISSON', 'AAA', 'Test', '31.51', 'http://placehold.it/150x150');

INSERT INTO `performance` (`id`, `date`, `service`, `tempsExecution`) VALUES
(1, '2016-06-03 11:57:58', 'fr.pizzeria.dao.pizza.PizzaDaoJpaDataImpl@4cdb8504', 61),
(2, '2016-06-03 11:58:12', 'fr.pizzeria.dao.pizza.PizzaDaoJpaDataImpl@4cdb8504', 29),
(3, '2016-06-03 12:01:30', 'List fr.pizzeria.dao.pizza.IPizzaDao.findAllPizzas()', 73),
(4, '2016-06-03 12:02:26', 'void fr.pizzeria.dao.pizza.IPizzaDao.saveNewPizza(Pizza)', 59);
