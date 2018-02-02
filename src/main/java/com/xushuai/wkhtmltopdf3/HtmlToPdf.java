package com.xushuai.wkhtmltopdf3;

import java.io.File;
import java.util.concurrent.FutureTask;

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
        boolean result = false;  
        try{  
            Process proc = Runtime.getRuntime().exec(cmd.toString());  
            FutureTask<Boolean> future = new FutureTask<Boolean>(new HtmlToPDFOperate(proc.getErrorStream()));
            new Thread(future).start();
            //int waitfor = proc.waitFor();  
            boolean mm = future.get();
            if(mm){
            	result = true;
            }
        }catch(Exception e){  
            result = false;  
            e.printStackTrace();  
        }  
          
        return result;  
    }  
}
