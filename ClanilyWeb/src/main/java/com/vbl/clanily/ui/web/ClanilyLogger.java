package com.vbl.clanily.ui.web;

public class ClanilyLogger {

	public static void LogMessage(Class<?> clazz, Exception e) {
		e.printStackTrace();
	}
	
	public static void LogMessage(Class<?> clazz, String message) {
		System.out.println(message);
	}
}
