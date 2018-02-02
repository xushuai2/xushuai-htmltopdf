package com.xushuai.htmltopdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

public class HtmlToXHtmlJsoup {
	public static String html2xhtml(String html) {
        Document doc = Jsoup.parse(html);
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);
        return doc.html();
    }
	
	
	public static void main(String[] args) throws Exception {
        File file = new File("D://湖北班仙桃大车协议2018-2-1.html");
        FileInputStream input = new FileInputStream(file);
        int size = input.available();
        byte[] buff = new byte[size];
        input.read(buff);
        input.close();
        String html = new String(buff, "utf-8");
        System.out.println("============html===================");
        
        String xhtml = html2xhtml(html);
        //String xhtml = html2xhtmlByJtidy(html);
        System.out.println("============xhtml===================");
        FileOutputStream ouput = new FileOutputStream("D://湖北班仙桃大车协议2018-2-1.xhtml");
        ouput.write(xhtml.getBytes("utf-8"));
        ouput.close();
    }
	
	/*public static String html2xhtmlByJtidy(String html) {  
        ByteArrayInputStream stream = new ByteArrayInputStream(html.getBytes());  
  
        ByteArrayOutputStream tidyOutStream = new ByteArrayOutputStream();  
        // 实例化Tidy对象  
        Tidy tidy = new Tidy();  
        // 设置输入  
        tidy.setInputEncoding("gb2312");  
        // 如果是true 不输出注释，警告和错误信息  
        tidy.setQuiet(true);  
        // 设置输出  
        tidy.setOutputEncoding("gb2312");  
        // 不显示警告信息  
        tidy.setShowWarnings(false);  
        // 缩进适当的标签内容。  
        tidy.setIndentContent(true);  
        // 内容缩进  
        tidy.setSmartIndent(true);  
        tidy.setIndentAttributes(false);  
        // 只输出body内部的内容  
        tidy.setPrintBodyOnly(true);  
        // 多长换行  
        tidy.setWraplen(1024);  
        // 输出为xhtml  
        tidy.setXHTML(true);  
        // 去掉没用的标签  
        tidy.setMakeClean(true);  
        // 清洗word2000的内容  
        tidy.setWord2000(true);  
        // 设置错误输出信息  
        tidy.setErrout(new PrintWriter(System.out));  
        tidy.parse(stream, tidyOutStream);  
        return tidyOutStream.toString();  
    }  */

    
}
