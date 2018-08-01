package com.example.demo.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@EnableAsync
@Slf4j
public class ScheduledTask {
	
	private static final ThreadLocal<SimpleDateFormat> localDateFromate = new ThreadLocal<SimpleDateFormat>(){

		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		
	};
	
	@Scheduled(cron="0 0/10 * * * ?")
	public void reportCurrentTime(){
		log.info("Scheduled run !  北京时间：{}", localDateFromate.get().format(new Date()));
	}

}
