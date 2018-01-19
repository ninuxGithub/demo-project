package com.example.demo;

import java.io.File;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Word2HtmlPDF {
	// 8 代表word保存成html
	// 17代表word保存成pdf
	public static final int WORD_HTML = 8;
	public static final int WORD_PDF = 17;

	public static boolean wordToHtml(String word, String html) {
		ActiveXComponent app = new ActiveXComponent("Word.Application");
		try {
			// 设置word应用程序不可见
			app.setProperty("Visible", new Variant(false));
			// documents表示word程序的所有文档窗口，（word是多文档应用程序）
			Dispatch docs = app.getProperty("Documents").toDispatch();
			// 打开要转换的word文件
			Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method,
					new Object[] { word, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
			// 作为html格式保存到临时文件
			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { html, new Variant(WORD_HTML) }, new int[1]);
			// 关闭word文件
			Dispatch.call(doc, "Close", new Variant(false));
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		} finally {
			// 关闭word应用程序
			app.invoke("Quit", new Variant[] {});
		}
		return true;
	}

	public static void wordToPDF(String docfile, String pdfFile) {
		// 启动word应用程序(Microsoft Office Word 2003)
		ActiveXComponent app = new ActiveXComponent("Word.Application");
		try {
			// 设置word应用程序不可见
			app.setProperty("Visible", new Variant(false));
			// documents表示word程序的所有文档窗口，（word是多文档应用程序）
			Dispatch docs = app.getProperty("Documents").toDispatch();
			// 打开要转换的word文件
			Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method,
					new Object[] { docfile, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
			// 作为html格式保存到临时文件
			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { pdfFile, new Variant(WORD_PDF) },
					new int[1]);
			// 关闭word文件
			Dispatch.call(doc, "Close", new Variant(false));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭word应用程序
			app.invoke("Quit", new Variant[] {});
		}
	}

	public static synchronized boolean convert(String word, String pdf) {
		boolean boo = wps2pdf(word, pdf);
		return boo;
	}

	public static synchronized boolean convertHtml(String word, String pdf) {
		boolean boo = wordToHtml(word, pdf);
		return boo;
	}

	public static boolean wps2pdf(String word, String pdf) {
		File pdfFile = new File(pdf);
		ActiveXComponent wps = null;
		Dispatch doc = null;
		try {

			wps = new ActiveXComponent("Word.Application");
			wps.setProperty("visible", new Variant(false));
			Dispatch docs = wps.getProperty("Documents").toDispatch();
			doc = Dispatch.call(docs, "Open", word, false, true).toDispatch();
			if (pdfFile.exists()) {
				pdfFile.delete();
			}
			Dispatch.call(doc, "SaveAs", pdf, 17);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("文件转化出错：" + ex.getMessage());
			return false;
		} finally {
			Dispatch.call(doc, "Close", false);
			if (wps != null) {
				wps.invoke("Quit", new Variant[] {});
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Word2HtmlPDF.convertHtml("C://XX证券研报模板.docx", "C://XX证券研报模板.html");

		Word2HtmlPDF.wordToPDF("C://XX证券研报模板.docx", "C://XX证券研报模板.pdf");
	}
}
