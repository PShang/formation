package fr.pizzeria.model;

import org.junit.Assert;
import org.junit.Test;

public class PizzaTest {

	@Test
	public void testToString() {
		Pizza p1 = new Pizza("IND", "L'indienne", 14.00, CategoriePizza.VIANDE);
		Pizza p2 = new Pizza("ORI", "L'orientale", 13.50, CategoriePizza.VIANDE);
		Pizza p3 = new Pizza("FRO", "La 4 fromages", 12.00, CategoriePizza.SANS_VIANDE);
		Pizza p4 = new Pizza("REI", "La Reine", 11.50, CategoriePizza.VIANDE);
		Pizza p5 = new Pizza("CAN", "La cannibale", 12.50, CategoriePizza.VIANDE);
		Pizza p6 = new Pizza("SAU", "La saumonetta", 15.50, CategoriePizza.POISSON);
		Pizza p7 = new Pizza("SAV", "La savoyarde", 13.00, CategoriePizza.VIANDE);
		Pizza p8 = new Pizza("MAR", "Margherita", 14.00, CategoriePizza.SANS_VIANDE);
		Pizza p9 = new Pizza("PEP", "Pépéroni", 12.50, CategoriePizza.VIANDE);

		Assert.assertEquals("IND -> L'indienne [Viande] (14.0€)", p1.toString());
		Assert.assertEquals("ORI -> L'orientale [Viande] (13.5€)", p2.toString());
		Assert.assertEquals("FRO -> La 4 fromages [Sans Viande] (12.0€)", p3.toString());
		Assert.assertEquals("REI -> La Reine [Viande] (11.5€)", p4.toString());
		Assert.assertEquals("CAN -> La cannibale [Viande] (12.5€)", p5.toString());
		Assert.assertEquals("SAU -> La saumonetta [Poisson] (15.5€)", p6.toString());
		Assert.assertEquals("SAV -> La savoyarde [Viande] (13.0€)", p7.toString());
		Assert.assertEquals("MAR -> Margherita [Sans Viande] (14.0€)", p8.toString());
		Assert.assertEquals("PEP -> Pépéroni [Viande] (12.5€)", p9.toString());
	}
}
