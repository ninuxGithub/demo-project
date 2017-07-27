package com.example.demo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;

@Configuration  
@EnableAutoConfiguration 
@Slf4j
public class RedisConfig {  
  
      
    @Bean  
    @ConfigurationProperties(prefix="spring.redis")  
    public JedisPoolConfig getRedisConfig(){  
        JedisPoolConfig config = new JedisPoolConfig();  
        return config;  
    }  
      
    @Bean  
    @ConfigurationProperties(prefix="spring.redis")  
    public JedisConnectionFactory getConnectionFactory(){  
        JedisConnectionFactory factory = new JedisConnectionFactory();  
        JedisPoolConfig config = getRedisConfig();  
        factory.setPoolConfig(config);  
        return factory;  
    }  
      
      
    @Bean  
    public RedisTemplate<?, ?> getRedisTemplate(){  
        RedisTemplate<?,?> template = new StringRedisTemplate(getConnectionFactory());  
        JedisConnectionFactory connectionFactory = getConnectionFactory();
        if(null != connectionFactory){
	        RedisConnection connection = getConnectionFactory().getConnection();
	        if(null != connection){
	        	log.info("==>JedisConnectionFactory bean init and Connect Redis Success...");  
	        }
        }else{
        	
        	log.info("==>connectionFactory is null ...");  
        }
        return template;  
    }  
} 
