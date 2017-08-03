package com.example.demo.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorTest {
	public static void main(String[] args) {
		System.out.println("main Run..");
		//相对的延迟
		scheduleWithFixedDelay();
		
		//固定的延迟
		scheduleAtFixedRate();
	}

	public static void scheduleWithFixedDelay() {

		final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
		// 响铃线程
		final Runnable beeper = new Runnable() {
			public void run() {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long time = (long) (Math.random() * 1000);
				// 输出线程的名字和使用目标对象及休眠的时间
				System.out.println(
						sf.format(new Date()) + "线程:" + Thread.currentThread().getName() + ":Sleeping" + time + "ms");
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
				}
			}
		};

		// 设定执行线程计划,初始10s延迟,每次任务完成后延迟10s再执行一次任务
		final ScheduledFuture<?> sFuture = scheduledExecutorService.scheduleWithFixedDelay(beeper, 3, 3, TimeUnit.SECONDS);

		// 10s后取消线程任务
		scheduledExecutorService.schedule(new Runnable() {
			public void run() {
				sFuture.cancel(true);
				scheduledExecutorService.shutdown();
			}
		}, 10, TimeUnit.SECONDS);

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " is shutdown..");

			}
		}, "shutDwonHook"));

	}

	public static void scheduleAtFixedRate() {
		// 声明线程池
		final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		// 响铃线程
		final Runnable beeper = new Runnable() {
			public void run() {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long time = (long) (Math.random() * 1000);
				// 输出线程的名字和使用目标对象及休眠的时间
				System.out.println(
						sf.format(new Date()) + " 线程：" + Thread.currentThread().getName() + ":Sleeping " + time + "ms");
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
				}
			}
		};
		// 计划响铃，初始延迟3s然后以3s的频率执行响铃
		final ScheduledFuture<?> beeperHandle = scheduledExecutorService.scheduleAtFixedRate(beeper, 3, 3, TimeUnit.SECONDS);

		// 取消响铃并关闭线程
		final Runnable cancelBeeper = new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + "CANCEL...");
				beeperHandle.cancel(true);
				scheduledExecutorService.shutdown();
			}
		};
		// 10s后执行scheduleAtFixedRate
		scheduledExecutorService.schedule(cancelBeeper, 10, TimeUnit.SECONDS);
	}

}
