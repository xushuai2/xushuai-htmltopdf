package com.xushuai.wkhtmltopdf3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class HtmlToPDFOperate implements Callable<Boolean>{
	private InputStream is;  

	public HtmlToPDFOperate() {
		super();
	}
	
	public HtmlToPDFOperate(InputStream is) {
		this.is = is;  
	}

	@Override
	public Boolean call() throws Exception {
		try{  
            //InputStreamReader isr = new InputStreamReader(is, "utf-8");  
			InputStreamReader isr = new InputStreamReader(is, "gbk");  
            BufferedReader br = new BufferedReader(isr);  
            String line = null;  
            while ((line = br.readLine()) != null) {  
            	if("Done".equals(line.toString().trim())){
            		System.out.println("******************success******************");
            		return true;
            	}else{
            		//System.out.println(line.toString()); //输出内容  
            	}
            }  
        }catch (IOException e){  
            e.printStackTrace();  
        }  
		return false;
	}

}
