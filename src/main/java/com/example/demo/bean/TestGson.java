package com.example.demo.bean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//[{"id":0,"list":[{"address":"address0","name":"java0"}]},{"id":1,"list":[{"address":"address1","name":"java1"}]}]

public class TestGson {

	public static void main(String[] args) {

		List<A> list = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			List<B> in = new ArrayList<>();
			in.add(new B("java" + i, "address" + i));
			list.add(new A(i, in));
		}
		
		
		String json = JSONObject.toJSONString(list);
		System.out.println(json);
		
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<A>>(){}.getType();
		List<A> lista = gson.fromJson(json, type);
		
		for (A a : lista) {
			System.out.println(a.getId()+"-->");
			List<B> listb = a.getList();
			for (B b : listb) {
				System.out.println(b.getAddress()+"--"+ b.getName());
			}
		}
	}

}
