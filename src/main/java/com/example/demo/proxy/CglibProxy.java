package com.example.demo.proxy;

import java.lang.reflect.Method;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {
	private Object target;

	public Object getInstance(Object target) {
		this.target = target;  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(this.target.getClass());  
        // 回调方法  
        enhancer.setCallback(this);  
        // 创建代理对象  
        return enhancer.create();  
//		this.target = target;
//		
//		Enhancer enhance = new Enhancer();
//		enhance.setSuperclass(target.getClass());
//		enhance.setCallback(this);
//		enhance.setClassLoader(target.getClass().getClassLoader());
//		return enhance.create();
	}

	@Override
	public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

		return methodProxy.invoke(target, args);
	}

}
