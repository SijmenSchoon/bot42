package com.bot42;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Account {
	private File location;
	private File tempLocation;
	private String username;
	
	public Account (String username) {
		(new File("accounts")).mkdirs();
		location = new File("accounts" + File.separator + username + "cfg");
		tempLocation = new File("accounts" + File.separator + username + "cfg");
		this.username = username;
	}
	
	public boolean addUser () throws IOException {
		if (location.exists()) return false;
		location.createNewFile();
		PrintWriter writer = new PrintWriter(new FileWriter(location));
		writer.write("username=\"" + username + "\"\n");
		writer.flush(); writer.close();
		return true;
	}
	
	public boolean setPassword (String newpass) throws IOException {
		if (!location.exists()) return false;
		String passhash = String.valueOf(newpass.hashCode());
		
		BufferedReader reader = new BufferedReader(new FileReader(location));
		PrintWriter writer = new PrintWriter(new FileWriter(tempLocation));
		
		String line = null;
		
		while ((line = reader.readLine()) != null) {
			if (!line.trim().startsWith("password=")) {
				writer.println(line);
				writer.flush();
			}
		}
		writer.write("password=\"" + passhash + "\"\n");
		writer.flush(); writer.close(); reader.close();
		
		location.delete();
		tempLocation.renameTo(location);
		
		return true;
	}
	
	public boolean checkPassword (String pass) throws Exception {
		if (!location.exists()) return false;
		String passhash = String.valueOf(pass.hashCode());
		BufferedReader reader = new BufferedReader(new FileReader(location));
		String configPasshash = "";
		while (reader.ready()) {
			String line = reader.readLine();
			if (line.contains("password=")) {
				configPasshash = line.split("=")[1].substring(1, line.split("=").length-2);
				break;
			}
		}
		reader.close();
		if (passhash.equals(configPasshash)) {
			return true;
		}
		return false;
	}
}
