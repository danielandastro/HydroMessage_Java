package com.hydro.main;

import java.io.IOException;
import java.util.Scanner;

import com.hydro.comm.server.Server;

public class ServerMain {
	public static void main(String[] args) throws IOException {
		Server server = Server.getInstance();
		server.start();
		
		System.out.println("SERVER SIDE");
		System.out.println("Type 'help' for list of commands available");

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("$");
			String input = scanner.nextLine().toLowerCase();
			switch (input) {

			case "quit":
				break;

			case "send":
				System.out.println("Enter message to send to clients");
				System.out.print("$");
				String message = scanner.next();
				server.BroadcastMessage(message);
				break;

			case "help":
				System.out.println("help - Display list of commands available");
				System.out.println("send - Broadcast message to the clients");
				System.out.println("quit - Quit the client");
			default:
				break;
			}

			if (input.equalsIgnoreCase("quit")) {
				scanner.close();
				break;
			}
				

		}
	}

}
