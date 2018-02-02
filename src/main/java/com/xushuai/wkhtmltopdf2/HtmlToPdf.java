package com.xushuai.wkhtmltopdf2;

import java.io.File;
import java.util.Map;
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
        //ExecutorService exec = Executors.newCachedThreadPool();  
        //List<Future<Map>> results=new ArrayList<Future<Map>>();
        boolean result = false;  
        try{  
            Process proc = Runtime.getRuntime().exec(cmd.toString());  
            
            FutureTask<Map> future = new FutureTask<Map>(new HtmlToPDFOperate(proc.getErrorStream(),"error"));
            new Thread(future).start();
            
            
//            results.add(exec.submit(new HtmlToPDFOperate(proc.getErrorStream(),"error")));  
//            results.add(exec.submit(new HtmlToPDFOperate(proc.getInputStream(),"input")));  
            
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
            int waitfor = proc.waitFor();  
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<waitfor="+waitfor);
            
            
            Map mm = future.get();
            System.out.println(mm.get("type")+"--"+mm.get("result"));  
            
            /*for(Future<Map> fs :results) {  
                try {  
                	Map mm = fs.get();
                    System.out.println(mm.get("type")+"--"+mm.get("result"));  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                } catch (ExecutionException e) {  
                    e.printStackTrace();  
                }  
            }  */
        }catch(Exception e){  
            result = false;  
            e.printStackTrace();  
        }  
          
        return result;  
    }  
}
