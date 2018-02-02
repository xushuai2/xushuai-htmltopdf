package com.xushuai.htmltopdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import com.openhtmltopdf.extend.FSSupplier;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder.FontStyle;

public class TaskThread implements Runnable {
	private static final String FONT_URL = "http://dl.qiyuesuo.com/fonts/simkai.ttf";
	private static final String FONT_URL_SUFFIX = "simkai.ttf";
	Semaphore semp;  
	int NO;  
	CountDownLatch countDownLatch;
	public TaskThread(Semaphore semp, int NO ,CountDownLatch countDownLatch) {  
		this.semp = semp;  
		this.NO = NO; 
		this.countDownLatch = countDownLatch;
	}  
	@Override
	public void run() {
		try {
			// 获取许可  
			semp.acquire();
			String path = "file:///D:/xushuai.xhtml";  
	        String pdfName = UUID.randomUUID().toString() + ".pdf";  
	        long startTime = System.currentTimeMillis();
	        exportToPdfBox2("file:///D:/xushuai.xhtml", "D:/xx/output.pdf");
	        System.out.println(NO+":耗时ms："+(System.currentTimeMillis()-startTime));
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
	
	public static void exportToPdfBox(String url, String out)
	{
            OutputStream os = null;
       
              try {
               os = new FileOutputStream(out);
       
               try {
                     PdfRendererBuilder builder = new PdfRendererBuilder();
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
              }
              catch (IOException e1) {
                     e1.printStackTrace();
              }
	}
	
	
	public static void exportToPdfBox2(String url, String out)
	{
            OutputStream os = null;
              try {
	               os = new FileOutputStream(out);
	               try {
	                     PdfRendererBuilder builder = new PdfRendererBuilder();
	                     
	                    String tmpdir = System.getProperty("java.io.tmpdir");
	                    //System.out.println("---"+tmpdir);
	                    final File file = new File(tmpdir,FONT_URL_SUFFIX);//默认字体名称
	         			if(!file.exists()){
	         				System.out.println("*****************************");
	         				URL url2 = new URL(FONT_URL);//默认字体
	         				HttpURLConnection conn = (HttpURLConnection)url2.openConnection();  
	         		        conn.setConnectTimeout(3*1000);
	         		        InputStream inputStreamConn = conn.getInputStream(); 
	         		        IOUtils.writeStream(inputStreamConn, new FileOutputStream(file));
	         		        IOUtils.safeClose(inputStreamConn);
	         			}
	         			
	       		         //Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(file));
//	       		         builder.useFont(font, "simkai");
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
			       		 }, "simkai", 400, FontStyle.NORMAL, true);
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
