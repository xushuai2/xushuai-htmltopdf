package com.xushuai.htmltopdf;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import javax.imageio.ImageIO;

public class IOUtils {
	public static final int READ_BUFFER_SIZE = 1024;

	/**
	 * 关闭流
	 * @param closeable
	 */
	public static void safeClose(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				
			}
		}
	}

	/**
	 * 删除文件
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file != null) {
			file.delete();
		}
	}

	/**
	 * 读取流中的字符到string
	 * @param in 该方法执行完成会关闭流
	 * @param charset
	 * @throws IOException
	 */
	public static String readStreamAsString(InputStream in, String charset) throws IOException {
		if (in == null) {
			return "";
		}

		Reader reader = null;
		Writer writer = new StringWriter();
		String result;

		char[] buffer = new char[1024];
		try {
			int n = -1;
			reader = new BufferedReader(new InputStreamReader(in, charset));
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
			result = writer.toString();
		} finally {
			safeClose(in);
			safeClose(reader);
			safeClose(writer);
		}
		return result;
	}

	/**
	 * 读取流中的字符到数组
	 * @param in 该方法执行完成会关闭流
	 * @return
	 * @throws IOException
	 */
	public static byte[] readStreamAsByteArray(InputStream in) throws IOException {

		if (in == null) {
			return new byte[0];
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[READ_BUFFER_SIZE];
		int len = -1;
		while ((len = in.read(buffer)) != -1) {
			output.write(buffer, 0, len);
		}
		output.flush();
		return output.toByteArray();
	}

	/**
	 * 读取流中的字符到数组
	 * @param in 该方法执行完成不会关闭流
	 * @param limit 读取大小限制
	 * @return
	 * @throws IOException
	 */
	public static byte[] readStreamAsByteArray(InputStream in, int limit) throws IOException {

		if (in == null) {
			return new byte[0];
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[READ_BUFFER_SIZE];
		int len = -1;
		int temp = limit - READ_BUFFER_SIZE;
		while ((len = in.read(buffer)) != -1) {
			output.write(buffer, 0, len);
			if(output.size() > temp) {
				if(output.size() == limit) {
					break;
				}

				byte[] buffer2 = new byte[limit - output.size()];
				while ((len = in.read(buffer2)) != -1) {
					output.write(buffer2, 0, len);
					if(output.size() == limit) {
						break;
					}
					buffer2 = new byte[limit - output.size()];
				}
				
				break;
			}
		}
		return output.toByteArray();
	}

	/**
	 * 将InputStream写入到OutputStream，写完后需要根据需要决定是否关闭流
	 * @param in
	 * @param output
	 * @throws IOException
	 */
	public static void writeStream(InputStream in, OutputStream output) throws IOException {
		if (in == null) {
			return;
		}

		byte[] buffer = new byte[READ_BUFFER_SIZE];
		int len = -1;
		while ((len = in.read(buffer)) != -1) {
			output.write(buffer, 0, len);
		}
		output.flush();
	}
	
	/**
	 * 将图片流写入到response中,写完后会自动关闭流
	 * @param in 该方法执行完成会关闭流
	 * @param response
	 * @param type 类型（png）
	 */
	public static void writeImage(InputStream in, OutputStream out, String type){
		try {
			BufferedImage bi = ImageIO.read(in);
			ImageIO.write(bi, type, out);
		} catch (Exception e) {
			
		} finally {
			safeClose(in);
			safeClose(out);
		}
	}

}

