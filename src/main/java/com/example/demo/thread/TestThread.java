package com.example.demo.thread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestThread extends Thread {

	public TestThread() {
		// 设置线程的名称
		super.setName("TestThread");
	}

	//alibaba推荐
	public static final ThreadLocal<DateFormat> localDateFormat = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		};
	};

	@Override
	public void run() {
		for(int i=0; i<10; i++){
			DateFormat dateFormat = localDateFormat.get();
			String formatStr = dateFormat.format(new Date());
			
			System.out.println(Thread.currentThread().getName() + " formatStr:"+formatStr + " dateFormat hash :"+dateFormat.hashCode());
		}
	}
	
	public static void main(String[] args) {
		new TestThread().start();
	}

}
