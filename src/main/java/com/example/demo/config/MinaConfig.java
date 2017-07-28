package com.example.demo.config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.http.HttpServerCodec;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class MinaConfig {

    private KeepAliveFilter keepAliveFilter;
    
    private static final Integer PORT = 3003;

    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }

    @Bean
    public IoHandler ioHandler() {
        return new ServerHandler();
    }

    @Bean
    public InetSocketAddress inetSocketAddress() {
        return new InetSocketAddress(PORT);
    }

    @Bean
    public IoAcceptor ioAcceptor() throws IOException {
    	IoAcceptor acceptor = new NioSocketAcceptor();
    	acceptor.getFilterChain().addLast("codec", new HttpServerCodec());
		acceptor.setHandler(ioHandler());
		acceptor.bind(new InetSocketAddress(8080));
		return acceptor;
    }
    
    
   /* @Bean
    public IoAcceptor ioAcceptor() throws IOException {
    	IoAcceptor acceptor = new NioSocketAcceptor();
    	acceptor.getFilterChain().addLast("logger", loggingFilter());
    	//PrefixedStringCodecFactory
    	//TextLineCodecFactory
    	acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
    	keepAliveFilter = new KeepAliveFilter(new HeartBeatFilter(), IdleStatus.BOTH_IDLE);
    	keepAliveFilter.setForwardEvent(true);
    	acceptor.getFilterChain().addLast("heartBeat", keepAliveFilter);
    	acceptor.setHandler(ioHandler());
    	acceptor.getSessionConfig().setReadBufferSize(2048);
    	acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);
    	
    	acceptor.bind(inetSocketAddress());
    	if (log.isDebugEnabled()) {
    		log.debug("server run");
    	}else if(log.isInfoEnabled()){
    		log.info("Mina server run...");
    	}
    	return acceptor;
    }*/
}
