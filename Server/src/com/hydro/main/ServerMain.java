package com.hydro.main;

import java.io.IOException;

import com.hydro.comm.server.Server;

public class ServerMain {
	public static void main(String[] args) throws IOException {
		Server server = Server.getInstance();
		server.start();
	}
}
