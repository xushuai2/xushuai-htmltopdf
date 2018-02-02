package com.xushuai.join;

import java.io.File;
import java.util.concurrent.TimeoutException;

public class HtmlToPdf {
	//wkhtmltopdf在系统中的路径  
    private static final String toPdfTool = "C:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";  
      
    /** 
     * html转pdf 
     * @param srcPath html路径，可以是硬盘上的路径，也可以是网络路径 
     * @param destPath pdf保存路径 
     * @return 转换成功返回true 
     */  
    public static boolean convert(String srcPath, String destPath){  
        File file = new File(destPath);  
        File parent = file.getParentFile();  
        //如果pdf保存路径不存在，则创建路径  
        if(!parent.exists()){  
            parent.mkdirs();  
        }  
          
        StringBuilder cmd = new StringBuilder();  
        cmd.append(toPdfTool);  
        cmd.append(" ");  
        cmd.append(srcPath);  
        cmd.append(" ");  
        cmd.append(destPath);  
        
        int wait = 0;
          
        boolean result = false;  
        try{  
        	long startTime1 = System.currentTimeMillis();
        	//接收Process的输入和错误信息时，需要创建另外的线程，否则当前线程会一直等待（在Tomcat中有这种现象）。
            Process proc = Runtime.getRuntime().exec(cmd.toString());  
            
            
            Worker worker = new Worker(proc);
            worker.start();
            
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());  
            long startTime2 = System.currentTimeMillis();
            System.out.println("startTime2-startTime1="+(startTime2-startTime1));
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());  
            long startTime3 = System.currentTimeMillis();
            System.out.println("startTime3-startTime2="+(startTime3-startTime2));
            error.start();  
            output.start();  
            long startTime4 = System.currentTimeMillis();
            System.out.println("startTime4-startTime3="+(startTime4-startTime3));
            //--------------------
            try {
                worker.join(500);
                if (worker.exit != null){
                	System.out.println("********************");
                	wait =  worker.exit;
                } else{
                    throw new TimeoutException();
                }
            } catch (InterruptedException ex) {
                worker.interrupt();
                Thread.currentThread().interrupt();
                throw ex;
            } finally {
            	proc.destroy();
            }
            //--------------------
            //int wait = proc.waitFor();  
            System.out.println("========wait======"+wait);
            long startTime5 = System.currentTimeMillis();
            System.out.println("startTime5-startTime4="+(startTime5-startTime4));
            
            if(wait == 0){
            	result = true;
            }
        }catch(Exception e){  
            result = false;  
            e.printStackTrace();  
        }  
          
        return result;  
    }  
    
    
    private static class Worker extends Thread {
        private final Process process;
        private Integer exit;
 
        private Worker(Process process) {
            this.process = process;
        }
 
        @Override
		public void run() {
            try {
                exit = process.waitFor();
            } catch (InterruptedException ignore) {
                return;
            }
        }
    }
}
