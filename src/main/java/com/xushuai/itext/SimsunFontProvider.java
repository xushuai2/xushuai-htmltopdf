package com.xushuai.itext;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

public class SimsunFontProvider extends XMLWorkerFontProvider {
	public final static String FONT_PATH = "D:/xutool/simsun.ttf";
	
	@Override
	public Font getFont(final String fontname, final String encoding, final boolean embedded, final float size,
			final int style, final BaseColor color) {
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Font font = new Font(bf, size, style, color);
		font.setColor(color);
		return font;
	}
}
