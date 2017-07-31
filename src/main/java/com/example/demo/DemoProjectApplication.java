package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.DispatcherType;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.servlet.MyListener;
import com.example.demo.servlet.SpringFilter;
import com.example.demo.servlet.SpringServlet;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@ServletComponentScan(basePackages="com.example.demo.servlet")
public class DemoProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoProjectApplication.class, args);
	}

	/**
	 * 注册Spring Servlet Bean 指定访问的 url :/spring/servlet
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean();
		bean.setServlet(new SpringServlet());
		bean.setName("springServlet");
		bean.addUrlMappings("/spring/servlet");
		bean.setOrder(0);
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean filterRegistrationBean(){
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new SpringFilter());
		bean.setName("springFilter");
		List<String> urls = new ArrayList<>();
		urls.add("/*");
		bean.setUrlPatterns(urls);
		log.info("springFilter run....");
		bean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
		return bean;
	}
	
	@Bean
	public ServletListenerRegistrationBean<MyListener> listenerRegistrationBean(){
		ServletListenerRegistrationBean<MyListener> bean = new ServletListenerRegistrationBean<>();
		bean.setListener(new MyListener());
		return bean;
	}
}
