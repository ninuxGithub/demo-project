package com.example.demo.proxy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.aop.TimeCounter;

@Controller
public class AopTestController {

	@RequestMapping("/jsonp")
	@ResponseBody
	@TimeCounter
	public String testJsonp() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "jsonp";
	}
}
