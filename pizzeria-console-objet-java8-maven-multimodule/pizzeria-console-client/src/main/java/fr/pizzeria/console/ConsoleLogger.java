package fr.pizzeria.console;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLogger {

	private ConsoleLogger() {}

	public static void out(Object msg) {
		System.out.println(msg);
	}

	public static void err(Object msg) {
		System.err.println(msg);
	}

	public static void err(String msg, Throwable e) {
		Logger.getGlobal().log(Level.SEVERE, msg, e);
		err(msg);
	}
}
