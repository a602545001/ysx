package com.rpc;

import java.io.IOException;
import java.net.Socket;

public class Handler implements Runnable {
	private Socket socket;

	public Handler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			System.out.println("新连接:" + socket.getInetAddress() + ":" + socket.getPort());
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("关闭连接:" + socket.getInetAddress() + ":" + socket.getPort());
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
