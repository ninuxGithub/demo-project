package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.annotation.TimeCounter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@Api("swaggerDemoController相关的api")
public class AopTestController {

	@GetMapping(value = "/jsonp")
	@ResponseBody
	@TimeCounter
	@ApiOperation(value = "testJsonp", notes = "测试jsonp")
    @ApiImplicitParam()
	public String testJsonp() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "jsonp";
	}
}
