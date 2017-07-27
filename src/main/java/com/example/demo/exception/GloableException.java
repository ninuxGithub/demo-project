package com.example.demo.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.bean.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GloableException {

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", e);
		log.debug("error message is {}", e.getMessage());
		mv.addObject("url", request.getRequestURL());
		mv.setViewName("error");
		return mv;
	}

	@ResponseBody
	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity businessExceptionHandler(HttpServletRequest request, BusinessException e) {
		ResponseEntity entity = new ResponseEntity(100, false, e.getMessage(), null);
		return entity;
	}

}
