package fr.operateurs.console;

import java.util.Scanner;

public class OperateursApp {
	public static void main(String[] args) {
		System.out.println("***** Application Opérateurs *****");
		Scanner scan = new Scanner(System.in);

		System.out.println("Veuillez saisir le premier nombre...");
		double d1 = scan.nextDouble();
		System.out.println("Veuillez saisir le second nombre...");
		double d2 = scan.nextDouble();

		System.out.println(d1 + " + " + d2 + " = " + (d1 + d2));
		System.out.println(d1 + " - " + d2 + " = " + (d1 - d2));
		System.out.println(d1 + " * " + d2 + " = " + (d1 * d2));
		System.out.println(d1 + " / " + d2 + " = " + (d1 / d2));
		System.out.println(d1 + " % " + d2 + " = " + (d1 % d2));

		scan.close();
	}
}
