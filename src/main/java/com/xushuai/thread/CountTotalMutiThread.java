package com.xushuai.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//Thread1、Thread2、Thread3、Thread4四条线程分别统计C、D、E、F四个盘的大小，所有线程都统计完毕交给Thread5线程去做汇总，应当如何实现？
public class CountTotalMutiThread {
	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(4);//工人的协作   
        ExecutorService es = Executors.newCachedThreadPool();//线程池    
        /*ExecutorCompletionService 是将 Executor和BlockQueue结合的jdk类，
	        其实现的主要目的是：提交任务线程，每一个线程任务直线完成后，将返回值放在阻塞队列中，
	        然后可以通过阻塞队列的take()方法返回 对应线程的执行结果！！*/
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(es);    
        SubTask subTask = null;
        for(int i=0;i<4;i++){
        	subTask =  new SubTask(i+1,latch);     
        	cs.submit(subTask);
        }
        // 添加结束，及时shutdown，不然主线程不会结束    
        es.shutdown();  
        
        try {
			latch.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}//等待所有工人完成工作  
        
        System.out.println("*****************");
        int total = 0;  
        Integer temp = 0;
        for(int i=0;i<4;i++){    
            try {    
            	temp = cs.take().get();
                total+=temp;    
            } catch (InterruptedException e) {    
                e.printStackTrace();    
            } catch (ExecutionException e) {    
                e.printStackTrace();    
            }    
        }    
            
        System.out.println(total);  
          
	}
}
    
/**   
* 一组并发任务   
*/     
class SubTask implements  Callable<Integer> {     
		private int type;    
		private CountDownLatch latch;   
        SubTask(int type,CountDownLatch latch) {     
            this.type = type; 
            this.latch = latch;
        }     
    
		@Override
		public Integer call() throws Exception {
			System.out.println("type="+type);
			 	if(type==1){    
			 		Thread.sleep(5000);   
		            System.out.println("C盘统计大小");  
		            latch.countDown();//工人完成工作，计数器减一    
		            return 1;    
		        }else if(type==2){    
		            Thread.sleep(4000);    
		            System.out.println("D盘统计大小");  
		            latch.countDown();//工人完成工作，计数器减一    
		            return 2;    
		        }else if(type==3){    
		        	Thread.sleep(6000);   
		            System.out.println("E盘统计大小");    
		            latch.countDown();//工人完成工作，计数器减一    
		            return 3;    
		        }else if(type==4){    
		        	Thread.sleep(8000);   
		            System.out.println("F盘统计大小");    
		            latch.countDown();//工人完成工作，计数器减一    
		            return 4;    
		        } 
			 	System.out.println("------------");
				return null;
		}     
}   