package com.hydro.comm.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private static Boolean SINGLE_INSTANCE = false;

	private static Client client;
	private final String SERVER_ADDRESS = "127.0.0.1";
	private final Integer PORT = 8080;
	private Socket socket;

	private Client() throws UnknownHostException, IOException {
		this.socket = new Socket(SERVER_ADDRESS, PORT);
	}

	public void start() throws IOException {
		// Initial connection thread
		new Thread(() -> {
			if (socket.isConnected()) {
				String ACK = "HELLO";
				try {
					OutputStream OS = socket.getOutputStream();
					DataOutputStream DOS = new DataOutputStream(OS);
					DOS.writeUTF(ACK);

				} catch (Exception e) {
					System.err.println("CLIENT EXCEPTION: " + e.toString());
				}
			} else {
				System.err.println("CLIENT NOT CONNECTED");
			}
		}).start();

		// receive from server thread
		new Thread(() -> {
			String response;
			try {
				InputStream IS = socket.getInputStream();
				DataInputStream DIS = new DataInputStream(IS);
				while (socket.isConnected()) {
					response = DIS.readUTF();
					System.out.println("FROM SERVER : "+ response);
				}
				System.out.println("client disconnected");
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}).start();
	}

	public static Client intance() throws UnknownHostException, IOException {
		if (SINGLE_INSTANCE) {
			return client;
		}
		return new Client();
	}

	public void sendMessage(String message) {
		if (socket.isConnected()) {
			new Thread(() -> {
				try {
					OutputStream OS = socket.getOutputStream();
					DataOutputStream DOS = new DataOutputStream(OS);
					DOS.writeUTF(message);
				} catch (Exception e) {
					System.out.println("Sending message error" + e.toString());
				}
			}).start();

		} else {
			System.out.println("Client not connected");
		}
	}

}
