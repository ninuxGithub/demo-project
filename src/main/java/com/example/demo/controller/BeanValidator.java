package com.example.demo.controller;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BeanValidator<T> implements Validator {

	private Class<?> clazz;

	public BeanValidator() {
		 // 使用反射技术得到T的真实类型  
		 Type[] genericInterfaces = this.getClass().getGenericInterfaces();
		 System.out.println(genericInterfaces[0].getClass().getName());
        ParameterizedType pt = (ParameterizedType) this.getClass().getAnnotatedSuperclass(); // 获取当前new的对象的 泛型的父类 类型  
        this.clazz = (Class<?>) pt.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类
		
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if (clazz.equals(clazz)) {
			return true;
		}
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			System.out.println(field.getName());
//			try {
//				if (null != t) {
//					Object value = field.get(t);
//					System.out.println(value.toString());
//					if(null != value){
//						Class<?> type = field.getType();
//						if(!value.getClass().isInstance(type)){
//							errors.rejectValue(field.getName(), null, field+"输入的不是"+ type.getName()+"类型");
//						}
//					}
//
//				}
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}

		}
	}

}
