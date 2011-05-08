package com.bot42;

public class Bot42 {
	public String nick = "Bot42";
	
	public static void main(String[] args) {
		
	}
	
	public static void send(String line) {
		System.out.println("[OUT] " + line);
	}
	
	public static String read() {
		String line = "";
		// space before [IN] to make it line up a bit nicer?
		System.out.println(" [IN] " + line);
		return line;
	}
}
