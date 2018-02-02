package com.xushuai.htmltopdf;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class demo {
	private static int thread_num = 100; 
	private static int client_num = 100;
	public static void main(String[] args) {
		//BingFaTest(); 
		String tmpdir = System.getProperty("java.io.tmpdir");
		System.out.println(tmpdir);
	}
	private static void BingFaTest() {
		final CountDownLatch countDownLatch = new CountDownLatch(client_num); 
		long start = System.currentTimeMillis();  
		ExecutorService exec = Executors.newCachedThreadPool();  
		// thread_num个线程可以同时访问  
		final Semaphore semp = new Semaphore(thread_num);  
		// 模拟client_num个客户端访问  
		for (int index = 0; index < client_num; index++) {  
			final int NO = index;  
			exec.execute(new TaskThread(semp, NO,countDownLatch));  
		}  
		try {  
            countDownLatch.await();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }     
		// 退出线程池  
		exec.shutdown();  
		long timeSpend = System.currentTimeMillis()-start;  
		System.out.println( "花费总时间: "+timeSpend +"ms" );
	}

	
	
	
}
