package com.example.demo.aop;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class TimerCounterAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(TimerCounterAspect.class);
	
	ThreadLocal<Map<ClassLoader, Long>> timeLocal =new ThreadLocal<>();
	
	@Pointcut(value="execution(* com.example.demo.proxy.AopTestController.*(..)) and @annotation(com.example.demo.aop.TimeCounter) ")
	public void targetMethodCutPoint(){}
	
	@Before(value="targetMethodCutPoint()")
	public void before(){
		logger.info("enter aop before method...");
		
	}
	
	
	@After(value="targetMethodCutPoint()")
	public void after(){
		logger.info("enter aop After method...");
	}
	
	@Around(value="targetMethodCutPoint()")
	public Object around(ProceedingJoinPoint joinPoint){
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		logger.info("execute around {}....", method.getName());

		Long startTime = System.currentTimeMillis();
		
		//exe target method
		try {
			Object result = joinPoint.proceed();
			
			Long endTime = System.currentTimeMillis();
			
			logger.info("execute around {} spend {} ms", method.getName(), (endTime-startTime));
			
			logger.info("execute method :{} the final result is :{}", method.getName(), result==null?null:result.toString());
			return result;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}
