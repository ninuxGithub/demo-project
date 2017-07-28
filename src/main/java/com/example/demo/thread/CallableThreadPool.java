package com.example.demo.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CallableThreadPool {
	private static int queueDeep = 4;

//	ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS,
//			new ArrayBlockingQueue<>(queueDeep , false), new ThreadPoolExecutor.DiscardOldestPolicy());
	ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 3, TimeUnit.MINUTES,
			new ArrayBlockingQueue<>(queueDeep , false), new ThreadPoolExecutor.DiscardOldestPolicy());

	public void createThreadPool(){

		
		List<Future<Integer>> futureList = new ArrayList<>();
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
			
			CallableTask callableTask = new CallableTask(i);
			Future<Integer> submit = tpe.submit(callableTask);
			futureList.add(submit);
		}
		for (Future<Integer> future : futureList) {
			try {
				System.out.println(future.get().intValue());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		tpe.shutdown();

	}

	private synchronized int getQueueSize(Queue<Runnable> queue) {
		return queue.size();
	}

	public static void main(String[] args) {
		CallableThreadPool test = new CallableThreadPool();
		test.createThreadPool();
	}
	
	class CallableTask implements Callable<Integer>{
		
		private int index;
		
		public CallableTask(){
			
		}
		public CallableTask(Integer index){
			this.index = index;
		}
		

		@Override
		public Integer call() throws Exception {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return this.index;
		}
		
	}
}
