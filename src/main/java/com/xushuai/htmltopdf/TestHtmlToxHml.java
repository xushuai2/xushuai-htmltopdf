package com.xushuai.htmltopdf;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Reader;

import org.w3c.tidy.Tidy;

public class TestHtmlToxHml {
	public static void main(String args[])
    {         
		TestHtmlToxHml t = new TestHtmlToxHml();             
        t.doTidy("D:\\contract.html");//转化开始            
    }

	public void doTidy(String f_in)
    {        
        BufferedInputStream sourceIn; //输入流
        ByteArrayOutputStream tidyOutStream; //输出流
        try
        {          
            Reader reader;  
            
            FileInputStream  fis  =  new   FileInputStream(f_in);//读文件
            ByteArrayOutputStream  bos  =  new  ByteArrayOutputStream();  
            int  ch;  
            while((ch=fis.read())!=-1)  
            {  
                    bos.write(ch);  
            }  
            fis.close();  
            byte[]  bs  =  bos.toByteArray();  
            bos.close();  
            //String hope_gb2312=new String(bs,"GB2312");//注意，默认是GB2312，所以这里先转化成GB2312然后再转化成其他的。            
            //byte[] hope_b=hope_gb2312.getBytes();
            //String basil=new String(hope_b,"utf-8");//将GB2312转化成UTF-8      
            
            
            String basil=new String(bs,"utf-8");//转化成UTF-8 
            byte[]basil_b=basil.getBytes();           
            ByteArrayInputStream stream = new ByteArrayInputStream(basil.getBytes());
            tidyOutStream = new ByteArrayOutputStream();
            Tidy tidy = new Tidy();
            tidy.setInputEncoding("UTF-8");
            tidy.setQuiet(true);                   
            tidy.setOutputEncoding("UTF-8");          
            tidy.setShowWarnings(false); //不显示警告信息
            tidy.setIndentContent(true);//
            tidy.setSmartIndent(true);
            tidy.setIndentAttributes(false);
            tidy.setWraplen(1024); //多长换行
            //输出为xhtml
            tidy.setXHTML(true);
            tidy.setErrout(new PrintWriter(System.out));
            tidy.parse(stream, tidyOutStream);
            DataOutputStream  to = new  DataOutputStream(new  FileOutputStream("D:\\bb-contract.xhtml"));  //将生成的xhtml写入 
            tidyOutStream.writeTo(to);
            //System.out.println(tidyOutStream.toString());

        }
        catch ( Exception ex )
        {
            System.out.println( ex.toString());
            ex.printStackTrace();
        }
    } 
}
