package com.bot42;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Bot42 {
	public static String nick = "Bot42";
	public static String host = "irc.kottnet.net";
	public static int port = 6667;
	public static Socket ircSocket;
	
	public static PrintWriter ircWriter = null;
	public static BufferedReader ircReader = null;
	
	public static void main(String[] args) {
		System.out.println("Bot42 IRC Bot by Vijfhoek and F16Gaming.");
		System.out.println("TODO: Add copyright information if we're going to use a license.");
		System.out.println("TODO: Add more TODO statements.");
		
		boolean connected = false;
		
		try {
			ircSocket = new Socket(host, port);
			ircWriter = new PrintWriter(ircSocket.getOutputStream());
			ircReader = new BufferedReader(new InputStreamReader(ircSocket.getInputStream()));
			
			write("NICK Bot42");
			write("USER javabot 0 * :Bot42");
			
			
			while (true) {
				String message = read();
				String[] splitMessage = message.split(" ");
				
				if (splitMessage[0].equals("PING")) {
					write("PONG " + splitMessage[1]);
				}
				if (!connected) {
					if (splitMessage[1].equals("376")) {
						connected = true;
						write("JOIN #Bot42");
					} else if (splitMessage[1].equals("433")) {
						write("NICK Bot42|2");
						nick = "Bot42|2";
					}
				}
				
			}
		} catch (UnknownHostException e) {
			System.out.println("[ERR] Can't connect to host " + host + ":" + port);
			return;
		} catch (IOException e) {
			System.out.println("[ERR] IO Exception " + e + " occured");
			return;
		}
	}
	
	public static void write(String line) {
		ircWriter.print(line + "\r\n");
		ircWriter.flush();
		System.out.println("[OUT] " + line);
	}
	
	public static String read() throws IOException {
		String line = "";
		line = ircReader.readLine();
		System.out.println(" [IN] " + line);
		return line;
	}
}
