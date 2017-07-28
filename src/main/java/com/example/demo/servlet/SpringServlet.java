package com.example.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
* @ClassName: com.example.demo.servlet.SpringServlet 
* @Description: TODO(加入到SpringBoot ServletRegistrationBean, 受到springBoot管理)
* @date 2017年7月27日 下午2:57:27 
*/
public class SpringServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2046567555438035869L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		req.getServletContext().log("spring servlet run...");
		writer.write("this is springServlet");
		
	}
	
	

}
