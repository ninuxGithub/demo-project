package com.example.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "myServlet", urlPatterns="/myServlet/*", description="通过注解实现Servlet")
public class MyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7339400014937946166L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		req.getServletContext().log("run myServlet....");
		writer.write("this is myServlet");
	}

}
