package com.example.demo.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener(value="myListener")
public class MyListener implements ServletRequestListener{

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		ServletRequest servletRequest = event.getServletRequest();
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		request.getServletContext().log("==>lister destory...");
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		ServletRequest servletRequest = event.getServletRequest();
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		request.getServletContext().log("==>lister init...");
	}

}
