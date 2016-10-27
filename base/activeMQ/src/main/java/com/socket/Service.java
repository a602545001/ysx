package com.socket;

import java.net.ServerSocket;
import java.net.Socket;

public class Service {

	public static void main(String[] args) {
		service();
	}

	public static void service() {
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			while (true) {
				Socket socket = null;

				socket = serverSocket.accept(); // 主线程获取客户端连接
				Thread workThread = new Thread(new Handler(socket)); // 创建线程
				workThread.start(); // 启动线程

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
