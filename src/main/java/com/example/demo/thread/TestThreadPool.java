package com.example.demo.thread;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * handler有四个选择：
 * <ol>
 * 
 * 	<li>ThreadPoolExecutor.AbortPolicy() 抛出java.util.concurrent.RejectedExecutionException异常</li>
 * 
 *  <li>ThreadPoolExecutor.CallerRunsPolicy() 当抛出RejectedExecutionException异常时，会调用rejectedExecution方法
 *  (如果主线程没有关闭，则主线程调用run方法,源码如下
 *  <pre>
 *  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
 *  		if (!e.isShutdown()) {
 *  			r.run();
 *  		}
 *   }
 *   </pre>
 *   )
 *  </li>
 *   
 *   <li>ThreadPoolExecutor.DiscardOldestPolicy() 抛弃旧的任务</li>
 *   
 *   
 *   <li>ThreadPoolExecutor.DiscardPolicy() 抛弃当前的任务</li>
 * </ol>  
 */
@Slf4j
public class TestThreadPool {
	private static int queueDeep = 4;
	
	ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueDeep, false), new ThreadPoolExecutor.DiscardOldestPolicy());
	public void createThreadPool() {
		/*
		 * 创建线程池，最小线程数为2，最大线程数为4，线程池维护线程的空闲时间为3秒，
		 * 使用队列深度为4的有界队列，如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，
		 * 然后重试执行程序（如果再次失败，则重复此过程），里面已经根据队列深度对任务加载进行了控制。
		 */

		// 向线程池中添加 10 个任务
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (getQueueSize(tpe.getQueue()) >= queueDeep) {
				log.info("队列已满，等3秒再添加任务");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			TaskThreadPool ttp = new TaskThreadPool(i);
			tpe.execute(ttp);
		}
		tpe.shutdown();
		
	}

	private synchronized int getQueueSize(Queue<Runnable> queue) {
		return queue.size();
	}
	
	public static void main(String[] args) {
		TestThreadPool test = new TestThreadPool();
		test.createThreadPool();
	}
	
	
	

	/** 
	* @ClassName: com.example.demo.thread.TaskThreadPool 
	* @Description: TODO(--)
	* @date 2017年7月28日 下午1:07:46 
	*/
	class TaskThreadPool implements Runnable {
		private int index;

		public TaskThreadPool() {
		
		}

		public TaskThreadPool(int index) {
			this.index = index;
		}

		public void run() {
			log.info(Thread.currentThread() + "exec index: {}" , index);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
