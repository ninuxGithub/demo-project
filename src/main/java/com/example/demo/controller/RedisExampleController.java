package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.ResponseEntity;
import com.example.demo.bean.User;
import com.example.demo.dao.IRedisService;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.JsonUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController 
public class RedisExampleController {

	@Autowired  
	private UserRepository userRepository;  
	
	@Autowired  
	private IRedisService redisService;  
	
	@ApiOperation(value="获取redis数据库所有用户信息")
	@GetMapping("/redis/users")  
	public ResponseEntity users(){  
		List<User> users = userRepository.findAll();
		ResponseEntity entity = new ResponseEntity(200,true,"",users);  
		return entity;  
	}  
	
	@ApiOperation(value="保存用户到redis", notes = "根据输入的value对象创建用户")
	@ApiImplicitParam(name="value", value="指定的用户名称", required= true, dataType="String")
	@GetMapping("/redis/setUser")  
	public ResponseEntity redisSet(@RequestParam("value")String value){  
		User repoUser = userRepository.findUserByName(value);
		if(null == repoUser){
			repoUser = new User(null,value, 18);
			userRepository.save(repoUser);
		}
		
		boolean isOk = redisService.set(value, JsonUtil.writeValueAsString(repoUser));
		
		return new ResponseEntity(isOk ? 200 : 500, isOk, isOk ? "success" : "error" , repoUser);  
	}  
	
	@ApiOperation(value="从redis获取到指定的用户（根据用户的名称）", notes = "根据输入的value返回指定的用户信息")
	@ApiImplicitParam(name="value", value="指定的用户名称", required= true, dataType="String")
	@GetMapping("/redis/getUser")  
	public ResponseEntity redisGet(@RequestParam("value")String value){  
		String userJson = redisService.get(value); 
		System.out.println(userJson);
		User user = JsonUtil.readStringToJavaBean(userJson, User.class);
		return new ResponseEntity(200, true,"success", user);  
	}  
}
