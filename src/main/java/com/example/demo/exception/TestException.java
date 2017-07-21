package com.example.demo.exception;

public class TestException {

	
	public static void main(String[] args)  {
		int i=10;
		int j=0;
		
		try {
			int y = i/j;
			System.out.println(y);
		} catch (Exception e) {
			//throw new RuntimeException(e.getMessage());
		}
		
		System.out.println("--------------------");
		
	}
}
