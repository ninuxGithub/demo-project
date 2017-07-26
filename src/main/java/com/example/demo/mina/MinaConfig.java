package com.example.demo.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <ol>
 * 	<li>step one...</li>
 * 	<li>step two...</li>
 * </ol>
 *
 */
/*@Configuration
public class MinaConfig {

	@Bean
	public LoggingFilter loggingFilter() {
		return new LoggingFilter();
	}

	@Bean
	public IoHandler ioHandler() {
		return new TimeServerHandler();
	}

	@Bean
	public InetSocketAddress inetSocketAddress() {
		return new InetSocketAddress(9123);
	}

	@Bean
	public IoAcceptor ioAcceptor() throws Exception {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("logger", loggingFilter());
		// acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(
		// new TextLineCodecFactory(Charset.forName("UTF-8"))));因为传byte[]所以去掉
		acceptor.setHandler(ioHandler());

		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		acceptor.bind(inetSocketAddress());
		System.out.println("服务器在端口：" + "已经启动");
		return acceptor;
	}

}*/
