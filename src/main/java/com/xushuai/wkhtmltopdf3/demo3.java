package com.xushuai.wkhtmltopdf3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.xushuai.wkhtmltopdf.TaskThread;

public class demo3 {
	private static int thread_num = 100; 
	private static int client_num = 50;
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			test();
		}
		System.out.println("总耗时ms："+(System.currentTimeMillis()-startTime));
//		BingFaTest();
	}

	private static void test() {
		String path = "D:\\x.xhtml";  
        if(path == null || path.equals("")){  
            return;  
        }  
        //String pdfName = UUID.randomUUID().toString() + ".pdf";  
        String pdfName = "123456789.pdf";  
        long startTime = System.currentTimeMillis();
        if(HtmlToPdf.convert(path,"D:\\xx\\" + pdfName)){  
        	System.out.println("success:"+ pdfName);
        }else{
        	System.out.println("error");
        }
        System.out.println("耗时ms："+(System.currentTimeMillis()-startTime));
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
