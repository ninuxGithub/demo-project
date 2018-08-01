package com.example.demo.config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter.MdcKey;
import org.apache.mina.http.HttpServerCodec;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.mina.HeartBeatFilter;
import com.example.demo.mina.ServerHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MinaConfig {

	@Value("${mina.port}")
	private int minaPort;

	/**
	 * bean initMethod, destroyMethod 防止端口被占用
	 * 
	 * //acceptor.getFilterChain().addLast("logger", new LoggingFilter());
	 * //acceptor.getFilterChain().addLast("codec", new HttpServerCodec());
	 * //acceptor.getFilterChain().addLast("heartBeat", keepAliveFilter);// 加入心跳
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean(initMethod = "bind", destroyMethod = "unbind")
	public NioSocketAcceptor ioAcceptor() throws IOException {
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		KeepAliveFilter keepAliveFilter = new KeepAliveFilter(new HeartBeatFilter(), IdleStatus.BOTH_IDLE);
		keepAliveFilter.setForwardEvent(true);// 不设置默认false
		keepAliveFilter.setRequestInterval(5);// 心跳频率，不设置则默认60s
		keepAliveFilter.setRequestTimeout(10);// 心跳超时时间，不设置则默认30s
		DefaultIoFilterChainBuilder filterChainBuilder = new DefaultIoFilterChainBuilder();
		Map<String, IoFilter> filters = new LinkedHashMap<>();
		filters.put("executor", new ExecutorFilter());
		filters.put("mdcInjectionFilter", new MdcInjectionFilter(MdcKey.remoteAddress));
		//filters.put("codecFilter", new ProtocolCodecFilter(new MyCodeFactory())); //codec 转换普通文字
		filters.put("codecFilter", new HttpServerCodec()); //识别http请求， 将message 转为Request
		//filters.put("loggingFilter", new LoggingFilter());
		filters.put("keepAliveFilter", keepAliveFilter);
		filterChainBuilder.setFilters(filters);
		acceptor.setFilterChainBuilder(filterChainBuilder);// 声明过滤器的集合
		acceptor.setHandler(new ServerHandler());// 绑定自己实现的handler
		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);
		acceptor.bind(new InetSocketAddress(minaPort));
		if (log.isDebugEnabled()) {
			log.debug("server run");
		} else if (log.isInfoEnabled()) {
			log.info("Mina server run...");
		}
		return acceptor;
	}
}
