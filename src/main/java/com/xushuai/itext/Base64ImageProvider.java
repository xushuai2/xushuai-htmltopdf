package com.xushuai.itext;

import java.util.Base64;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;

public class Base64ImageProvider extends AbstractImageProvider {

	@Override
	public Image retrieve(String src) {
		int pos = src.indexOf("base64,");
		try {
			if (src.startsWith("data") && pos > 0) {
				byte[] img = Base64.getDecoder().decode(src.substring(pos + 7));
				return Image.getInstance(img);
			} else {
				Image instance = Image.getInstance(src);
				return instance;
			}
		} catch (BadElementException ex) {
			return null;
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public String getImageRootPath() {
		return null;
	}
}
