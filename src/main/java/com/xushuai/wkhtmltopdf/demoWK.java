package com.xushuai.wkhtmltopdf;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class demoWK {
	private static int thread_num = 100; 
	private static int client_num = 100;
	public static void main(String[] args) {
		long start = System.currentTimeMillis();  
		int num = 1;
		for(int i=0;i<num;i++){
			test(i);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("总耗时ms："+(endTime-start));
		System.out.println("平均耗时ms："+(endTime-start)/num);
		/*BingFaTest(); 
		try {
			Thread.sleep(1000000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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

	private static void test(int num) {
		String path = "D:\\湖北班仙桃大车协议2018-2-1.html";  
        if(path == null || path.equals("")){  
            return;  
        }  
        long startTime = System.currentTimeMillis();
        if(HtmlToPdf.convert(0,path,"D:\\湖北班仙桃大车协议2018-2-1.html-wk.pdf")){  
        	System.out.println("success");
        }else{
        	System.out.println("error");
        }
        System.out.println(num+"耗时ms："+(System.currentTimeMillis()-startTime));
	}
	
	
	
}
