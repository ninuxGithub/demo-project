package com.example.demo.config;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TcpClient {
	public static void main(String[] args) throws IOException {

		Socket socket = null;
		OutputStream out = null;

		try {

			socket = new Socket("127.0.0.1", 3003);
			out = socket.getOutputStream();

			// 请求服务器
			String lines = "床前明月光\n疑是地上霜\n举头望明月\n低头思故乡\n";
//			String lines = "床前明月光\r\n疑是地上霜\r\n举头望明月\r\n低头思故乡\r\n";
			byte[] outputBytes = lines.getBytes("UTF-8");
			out.write(outputBytes);
			out.flush();

		} finally {
			// 关闭连接
			out.close();
			socket.close();
		}

	}

}
