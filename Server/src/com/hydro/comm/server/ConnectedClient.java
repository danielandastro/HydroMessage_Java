package com.hydro.comm.server;

import java.net.Socket;

public class ConnectedClient {
	private Socket clientSocket;
	private String userId;

	public ConnectedClient(Socket clientSocket, String userId) {
		super();
		this.clientSocket = clientSocket;
		this.userId = userId;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
