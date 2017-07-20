package com.example.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkInvocationHandler implements InvocationHandler{
	private Object target;
	
	public JdkInvocationHandler(Object target) {
		this.target = target;
	}
	
	public  Object getInstance(){
		Object newProxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
		return newProxyInstance;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		method.invoke(target, args);
		
		return null;
	}

}
