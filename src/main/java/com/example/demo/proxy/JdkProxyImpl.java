package com.example.demo.proxy;

import com.example.demo.annotation.TimeCounter;

public class JdkProxyImpl implements JdkProxy{

	@Override
	@TimeCounter
	public void doSth() {
		System.out.println("i am proxy...");
	}

}
