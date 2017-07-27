package com.example.demo.dao;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface IRedisService {

	boolean set(String key, String value);

	boolean set(String key, String value, long timeout, TimeUnit unit);

	String get(String key);

	boolean expire(String key, long expire, TimeUnit unit);

	<T> boolean setList(String key, List<T> list);

	<T> List<T> getList(String key, Class<T> clz);

	long lpush(String key, Object obj);

	long rpush(String key, Object obj);

	String lpop(String key);

	void remove(String key);

	Set<String> keys(String pattern);

}