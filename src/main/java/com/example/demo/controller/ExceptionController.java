package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.BusinessException;

@RestController
public class ExceptionController {
	
	@RequestMapping("/testException")
	public Integer getSum(){
		int a=10,b=0;
		return a/b;
	}
	
	
	@RequestMapping("/testBusinessException")
	public Integer getBy(){
		int a=10,b=0;
		if(b==0){
			throw new BusinessException("被除数为0");
		}
		return a/b;
	}

}
