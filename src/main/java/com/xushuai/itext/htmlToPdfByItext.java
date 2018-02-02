package com.xushuai.itext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.TagProcessorFactory;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.xushuai.htmltopdf.IOUtils;

public class htmlToPdfByItext {
	private static final String BREAK_PAGE_TAG = "_ueditor_page_break_tag_";//分页标识符
	public static void main(String[] args) {
		long start = System.currentTimeMillis();  
		int num =1;
		boolean flag = false;
		for(int i=0;i<num;i++){
			flag = itextTest(i);
		}
		if(flag){
			System.out.println("---------------------success");
		}else{
			System.out.println("---------------------error");
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("总耗时ms："+(endTime-start));
		System.out.println("平均耗时ms："+(endTime-start)/num);
		
	}


	private static boolean itextTest(int i) {
		boolean result = false;
		long start = System.currentTimeMillis();  
		InputStream in = null;
		ByteBuffer html = null;
		try {
			in = new FileInputStream("D://湖北班仙桃大车协议2018-2-1.html");
			ByteArrayOutputStream outtemp = new ByteArrayOutputStream();
		    byte[] buffer = new byte[1024 * 4];
		    int n = 0;
		    while ((n = in.read(buffer)) != -1) {
		    	outtemp.write(buffer, 0, n);
		    }
			html = ByteBuffer.wrap(outtemp.toByteArray());
			html = convertPdf(html);
			IOUtils.safeClose(outtemp);
		} catch (IOException e) {
			System.out.println("---------------------"+e.getMessage());
		} catch (DocumentException e) {
			System.out.println("---------------------"+e.getMessage());
		} finally{
			
		}
		
		if(html == null){
			return result;
		}
		
		OutputStream outputPdf = null;
		try {
			outputPdf = new FileOutputStream(new File("D://itext111-2.pdf"));
			IOUtils.writeStream(new ByteArrayInputStream(html.array()), outputPdf);
		} catch (Exception e) {
			System.out.println("---------------------"+e.getMessage());
			return result;
		}finally{
			IOUtils.safeClose(outputPdf);
		}
		result = true;
		System.out.println(i+"耗时ms："+(System.currentTimeMillis()-start));
		return result;
	}
	
	
	/**
	 * 将html数据转换成pdf数据
	 * @param htmlData
	 */
	public static ByteBuffer convertPdf(ByteBuffer htmlData) throws IOException, DocumentException {
		return pdfData(htmlData, false, null,true);
	}

	private static ByteBuffer pdfData(ByteBuffer htmlData, Boolean isParam, Map<String, String> params ,Boolean isOldTemplate) throws IOException, DocumentException {

		ByteArrayOutputStream out = null;
		try {
			//htmlData = readyConvert(htmlData, isParam, params,isOldTemplate);
			out = new ByteArrayOutputStream();
			com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
			// CSS
			CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
			// HTML
			CssAppliers cssAppliers = new CssAppliersImpl(getFontsProvider());
			HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
			TagProcessorFactory factory = Tags.getHtmlTagProcessorFactory();

			htmlContext.setTagFactory(factory);
			htmlContext.setImageProvider(getImageProvider());
			htmlContext.autoBookmark(false);
			// Pipelines
			PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
			HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
			CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
			// XML Worker
			XMLWorker worker = new XMLWorker(css, true);
			XMLParser p = new XMLParser(worker);
			p.parse(new ByteArrayInputStream(htmlData.array()), Charset.forName("UTF-8"));
			// close
			document.close();
		}catch (Exception e) {
			System.out.println("---"+out.toByteArray().length);
			System.out.println("-----------pdfData------异常："+e.getMessage());
			return null;
		}finally {
			try {
				if(out != null){
					out.close();
				}
			} catch (IOException e) {
				System.out.println("io is error when html data to pdf data !! "+ e);
			}
		}
		return ByteBuffer.wrap(out.toByteArray());

	}
	
	
	private static Base64ImageProvider getImageProvider() {
		return new Base64ImageProvider();
	}

	private static SimsunFontProvider getFontsProvider() {
		return new SimsunFontProvider();
	}
	
	/**
	 * 转换pdf时预处理html数据
	 */
	private static ByteBuffer readyConvert(ByteBuffer oldHtmlData, Boolean isParam, Map<String, String> params,Boolean isOldTemplate)
			throws UnsupportedEncodingException {
		return htmlTemplOperateOpen(oldHtmlData, isParam, params);
	}

	/**
	 * 
	 * @param oldHtmlData
	 * @param isParam
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static ByteBuffer htmlTemplOperateOpen(ByteBuffer oldHtmlData, Boolean isParam, Map<String, String> params)
			throws UnsupportedEncodingException {
		String htmlStr = new String(oldHtmlData.array(), "UTF-8");
		// ①：替换分页符
		htmlStr = htmlStr.replaceAll(BREAK_PAGE_TAG, "<h1 style='page-break-before: always'></h1>");
		Document doc = Jsoup.parse(htmlStr, "UTF-8");
		// ④：返回修改后的html数据
		return ByteBuffer.wrap(doc.html().getBytes("UTF-8"));
	}

}
