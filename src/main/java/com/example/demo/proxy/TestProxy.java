package com.example.demo.proxy;

public class TestProxy {

	public static void main(String[] args) {
		
		//jdk proxy
		JdkProxyImpl jdkProxyImpl = new JdkProxyImpl();
		System.out.println(jdkProxyImpl+ "   | hashcode:"+ jdkProxyImpl.hashCode());
		JdkProxy instance = (JdkProxy) new JdkInvocationHandler(new JdkProxyImpl()).getInstance();
		
		if(null == instance){
			System.out.println(instance);
		}
		instance.doSth();
		
		//cglib proxy  ：代理对象和被代理对象拥有这相同的hashCode , == false
		
		Book obook =new Book();
		Book book = (Book) new CglibProxy().getInstance(obook);
		System.out.println("proxy "+ book.hashCode()+ " obook: "+ obook.hashCode());
		System.out.println(book==obook);
		book.print("aaaaaaa");
		
	}

}
