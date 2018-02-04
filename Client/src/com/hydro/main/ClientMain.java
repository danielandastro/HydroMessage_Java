package com.hydro.main;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.hydro.comm.client.Client;

public class ClientMain {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Client client = Client.intance();
		client.start();
		
		System.out.println("Client SIDE");
		System.out.println("Type 'help' for list of commands available");

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("$");
			String input = scanner.next().toLowerCase();
			switch (input) {

			case "quit":
				break;

			case "send":
				System.out.println("Enter message to send to server");
				System.out.print("$");
				String message = scanner.next();
				client.sendMessage(message);
				break;

			case "help":
				System.out.println("help - Display list of commands available");
				System.out.println("quit - Quit the client");
				System.out.println("send - Send message to server");
			default:
				break;
			}

			if (input.equalsIgnoreCase("quit"))
				break;

		}
	}

}
