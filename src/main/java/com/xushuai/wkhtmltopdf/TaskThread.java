package com.xushuai.wkhtmltopdf;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class TaskThread implements Runnable {
	Semaphore semp;  
	int NO;  
	CountDownLatch countDownLatch;
	public TaskThread(Semaphore semp, int NO ,CountDownLatch countDownLatch) {  
		this.semp = semp;  
		this.NO = NO; 
		this.countDownLatch = countDownLatch;
	}  
	public void run() {
		try {
			// 获取许可  
			semp.acquire();
			String path = "D:\\111.html";  
	        String pdfName = UUID.randomUUID().toString() + ".pdf";  
	        long startTime = System.currentTimeMillis();
	        if(HtmlToPdf.convert(NO,path,"D:\\xx\\" + pdfName)){  
	        	System.out.println(NO+":耗时ms："+(System.currentTimeMillis()-startTime));
	        }else{
	        	System.out.println(">>>>>>>>>>>>>error>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	        }
	        // 释放 
	        semp.release(); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
            if(countDownLatch != null) {
            	countDownLatch.countDown();
            }
        }
	}

}
