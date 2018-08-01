package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.bean.ResponseEntity;
import com.example.demo.thread.AsyncTask;

@Controller
public class AsyncTaskController {
	
	@Autowired
	private AsyncTask asyncTask;
	
	@GetMapping(value = "/asyncTask")
	@ResponseBody
	public ResponseEntity testAsync() throws Exception{
		List<String> resultList = new ArrayList<>();
		List<Future<String>> list =new ArrayList<>();
		try {
			list.add(asyncTask.doTaskOne());
			list.add(asyncTask.doTaskTwo());
			list.add(asyncTask.doTaskThree());
			for (Future<String> future : list) {
				String result = future.get();
				resultList.add(result);
			}
			
			return new ResponseEntity(200, true, "success", resultList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
