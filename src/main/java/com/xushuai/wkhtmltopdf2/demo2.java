package com.xushuai.wkhtmltopdf2;

import java.util.UUID;

public class demo2 {
	public static void main(String[] args) {
		String path = "D:\\111.html";  
        if(path == null || path.equals("")){  
            return;  
        }  
        String pdfName = UUID.randomUUID().toString() + ".pdf";  
          
        if(HtmlToPdf.convert(path,"D:\\" + pdfName)){  
        	System.out.println("over:"+ pdfName);
        }else{
        	System.out.println("error:"+ pdfName);
        }
	}
}
