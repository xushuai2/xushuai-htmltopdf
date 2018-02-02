package com.xushuai.spring_boot_starter_xu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.openhtmltopdf.extend.FSSupplier;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder.FontStyle;
import com.xushuai.htmltopdf.IOUtils;
public class TestHtmlToPdf {
	private static final String FONT_URL = "http://dl.qiyuesuo.com/fonts/simkai.ttf";
	private static String FONT_URL_SUN = "D:/xutool";
	private static final String FONT_URL_SUFFIX = "simkai.ttf";
	private static final String FONT_TYPE = "simkai";
	public static void main(String[] args)
	{
		long startTime1 = System.currentTimeMillis();
		int num = 1;
		for(int i=0;i<num;i++){
			long startTime = System.currentTimeMillis();
			exportToPdfBox("file:///D:/111_xushaui.xhtml", "D:/111_xushaui--open.pdf");
			System.out.println(i+"耗时ms："+(System.currentTimeMillis()-startTime));
		}
		long endTime = System.currentTimeMillis();
		System.out.println("总耗时ms："+(endTime-startTime1));
		System.out.println("平均耗时ms："+(endTime-startTime1)/num);
	}
	
	public static void exportToPdfBox(String url, String out)
	{
            OutputStream os = null;
              try {
	               os = new FileOutputStream(out);
	               try {
	                     PdfRendererBuilder builder = new PdfRendererBuilder();
	                     
	                     String tmpdir = System.getProperty("java.io.tmpdir");
	                     //System.out.println("---"+tmpdir);
	                     //默认字体名称
	                     final File file = new File(tmpdir,FONT_URL_SUFFIX);
	         			 if(!file.exists()){
	         				System.out.println("*****************************");
	         				URL url2 = new URL(FONT_URL);//默认字体
	         				HttpURLConnection conn = (HttpURLConnection)url2.openConnection();  
	         		        conn.setConnectTimeout(3*1000);
	         		        InputStream inputStreamConn = conn.getInputStream(); 
	         		        IOUtils.writeStream(inputStreamConn, new FileOutputStream(file));
	         		        IOUtils.safeClose(inputStreamConn);
	         			 }

		       		     builder.useFont(new FSSupplier<InputStream>() {
			       		       @Override
			       		       public InputStream supply() {
			       		           try {
									return new FileInputStream(file);
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
								return null;
			       		       }
			       		 }, FONT_TYPE, 400, FontStyle.NORMAL, true);
		       		     
		       		     
		       		     
		       		     
		       		     
		       		     //字体simsun
		       		     final File simsunfile = new File(FONT_URL_SUN,"simsun.ttf");
		       		     
			       		 if(!file.exists()){
			       			System.out.println("simsun.ttf is null");  
			       		 }
			       		 builder.useFont(new FSSupplier<InputStream>() {
			       		       @Override
			       		       public InputStream supply() {
			       		           try {
									return new FileInputStream(simsunfile);
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
								return null;
			       		       }
			       		 }, "simsun", 400, FontStyle.NORMAL, true);
			       		 
		       		     
	                     builder.withUri(url);
	                     builder.toStream(os);
	                     builder.run();
	               } catch (Exception e) {
	                     e.printStackTrace();
	               } finally {
	                     try {
	                            os.close();
	                     } catch (IOException e) {
	                     }
	               }
              }catch (IOException e1) {
                     e1.printStackTrace();
              }
	}


}
