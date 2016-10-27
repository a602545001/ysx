package com.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Sender {

	public static void main(String[] args) {
		try {
			Socket socket=new Socket("0.0.0.0",8080);
//			socket.connect(sock, sa);
//			socket.bind(bindpoint);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
