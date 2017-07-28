package com.example.demo.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

public class SpringFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		request.getServletContext().log("throu springFiter...");
		doSometh();
		filterChain.doFilter(request, response);
	}

	private void doSometh() {
		RequestAttributes requestAttribute = RequestContextHolder.getRequestAttributes();

		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttribute;
		HttpServletRequest request = servletRequestAttributes.getRequest();

		ServletContext servletContext = request.getServletContext();

		String requestURI = request.getRequestURI();

		servletContext.log(requestURI + " was filtered By springFiter");
	}

}
