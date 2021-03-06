package com.hydro.comm.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

	private static Boolean SINGLE_INSTANCE = false;
	private static int serverPort = 8080;

	private ServerSocket serverSocket;
	private ConcurrentHashMap<String, ConnectedClient> connectedClients;

	private static Server server;

	private Server() throws IOException {
		this.connectedClients = new ConcurrentHashMap<>();
		this.serverSocket = new ServerSocket(serverPort);
	}

	public void start() {
		new Thread(() -> {

			while (true) {
				try {
					Socket socket = serverSocket.accept();
					socket.setKeepAlive(true);
					String address = socket.getInetAddress().getHostAddress();
					ConnectedClient connectedClient = new ConnectedClient(socket, address);

					if (!connectedClients.containsKey(address)) {
						connectedClients.put(address, connectedClient);
					}
					try {
						InputStream inputStream = socket.getInputStream();
						DataInputStream fromClient = new DataInputStream(inputStream);
						while (socket.isConnected()) {
							String response = fromClient.readUTF();
							System.out.println("From client : " + response);
						}
					} catch (Exception e) {
						System.err.println(e.toString());
					}

				} catch (Exception e) {
					System.err.println("SERVER ERROR: " + e.toString());
				}
			}
		}).start();
	}

	public static Server getInstance() throws IOException {
		if (SINGLE_INSTANCE) {
			return server;
		}
		return new Server();
	}

	public void BroadcastMessage(String message) throws IOException {
		new Thread(() -> {
			Iterator clientsIt = connectedClients.entrySet().iterator();	
			while (clientsIt.hasNext()) {
				Map.Entry<String, ConnectedClient> pair = (Map.Entry<String, ConnectedClient>) clientsIt.next();
				Socket clientSocket = pair.getValue().getClientSocket();
				try {
					OutputStream OS = clientSocket.getOutputStream();
					DataOutputStream DOS = new DataOutputStream(OS);
					DOS.writeUTF(message);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}).start();
	}

}