package com.example.demo.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {

	public static void main(String[] args) throws Exception {
		String[] arr = new String[] { "127.0.0.1", "3003" };

		// Create TCP/IP connector.
		NioSocketConnector connector = new NioSocketConnector();

		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MyCodeFactory(Charset.forName("UTF-8")))); // 设置编码过滤器
		connector.setConnectTimeoutMillis(30 * 1000L);
		connector.setHandler(new MonitorClientHandler());// 设置事件处理器
		ConnectFuture cf = connector.connect(new InetSocketAddress(arr[0], Integer.parseInt(arr[1])));//建立连接 

		cf.awaitUninterruptibly();//等待连接创建完成 
		
		cf.getSession().write("hello,测试！");//发送消息

		cf.getSession().getCloseFuture().awaitUninterruptibly();// 等待连接断开

		connector.dispose();
	}
}
