package com.hydro.main;

import java.io.IOException;
import java.net.UnknownHostException;

import com.hydro.comm.client.Client;

public class ClientMain {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Client client  = Client.intance();
		client.start();
	}

}
