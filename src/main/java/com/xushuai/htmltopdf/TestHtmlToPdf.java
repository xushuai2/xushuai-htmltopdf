package com.xushuai.htmltopdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;


public class TestHtmlToPdf {

		public static void main(String[] args)
		{
			for(int i=0;i<1;i++){
				long startTime = System.currentTimeMillis();
				exportToPdfBox("file:///D:/ddd.html", "D:/22.pdf");
				System.out.println("耗时ms："+(System.currentTimeMillis()-startTime));
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
	              }catch (IOException e1) {
	                     e1.printStackTrace();
	              }
		}


}
