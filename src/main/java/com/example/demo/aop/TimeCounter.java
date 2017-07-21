package com.example.demo.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 作用在方法上面 */
@Target(ElementType.METHOD)
/** 保留到运行时 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeCounter {

}
