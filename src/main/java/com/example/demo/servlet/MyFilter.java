package com.example.demo.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 过滤所有的路径
 * 
 * @ClassName: com.example.demo.servlet.MyFilter
 * @Description: TODO(--)
 * @date 2017年7月27日 下午3:59:09
 */
@WebFilter(displayName = "myFilter", urlPatterns = "/*")
public class MyFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		request.getServletContext().log("==>throu myFilter...");
		filterChain.doFilter(request, response);
		// response.getWriter().write("filter");
	}

}
