package com.xushuai.wkhtmltopdf2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class HtmlToPDFOperate implements Callable<Map>{
	private InputStream is;  
	private String type;

	public HtmlToPDFOperate() {
		super();
	}
	
	public HtmlToPDFOperate(InputStream is, String type) {
		this.is = is;  
		this.type = type;
	}

	public Map call() throws Exception {
		Map m = new HashMap<String, Object>();
		m.put("type", type);
		m.put("result", false);
		try{  
            InputStreamReader isr = new InputStreamReader(is, "utf-8");  
            BufferedReader br = new BufferedReader(isr);  
            String line = null;  
            while ((line = br.readLine()) != null) {  
            	if("Done".equals(line.toString().trim())){
            		System.out.println("******************success******************");
            		m.put("result", true);
            	}else{
            		System.out.println(line.toString()); //输出内容  
            	}
            }  
        }catch (IOException e){  
            e.printStackTrace();  
        }  
		return m;
	}

}
